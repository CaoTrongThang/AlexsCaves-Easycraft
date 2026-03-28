package net.minecraftforge.network.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleChannel {
    private final ResourceLocation id;
    private final Map<Integer, Registration<?>> registrationsByIndex = new HashMap<>();
    private final Map<Class<?>, Registration<?>> registrationsByType = new HashMap<>();
    private boolean serverReceiverRegistered;
    private boolean clientReceiverRegistered;

    public SimpleChannel(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation id() {
        return id;
    }

    public synchronized <MSG> void registerMessage(int index, Class<MSG> type, BiConsumer<MSG, FriendlyByteBuf> writer, Function<FriendlyByteBuf, MSG> reader, BiConsumer<MSG, Supplier<NetworkEvent.Context>> handler) {
        Registration<MSG> registration = new Registration<>(index, type, writer, reader, handler);
        registrationsByIndex.put(index, registration);
        registrationsByType.put(type, registration);
        ensureServerReceiver();
        ensureClientReceiver();
    }

    public <MSG> void sendToServer(MSG message) {
        Registration<MSG> registration = getRegistration(message);
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(registration.index());
        registration.writer().accept(message, buf);
        ClientAccess.sendToServer(id, buf);
    }

    public <MSG> void sendTo(MSG message, ServerPlayer player, NetworkDirection direction) {
        if (direction != NetworkDirection.PLAY_TO_CLIENT) {
            throw new UnsupportedOperationException("SimpleChannel.sendTo only supports PLAY_TO_CLIENT on Fabric");
        }
        Registration<MSG> registration = getRegistration(message);
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(registration.index());
        registration.writer().accept(message, buf);
        ServerPlayNetworking.send(player, id, buf);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void handleClientbound(FriendlyByteBuf buf) {
        int index = buf.readVarInt();
        Registration registration = registrationsByIndex.get(index);
        if (registration == null) {
            return;
        }
        Object message = registration.reader().apply(buf);
        dispatch(registration, message, null, NetworkDirection.PLAY_TO_CLIENT);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void handleServerbound(ServerPlayer player, FriendlyByteBuf buf) {
        int index = buf.readVarInt();
        Registration registration = registrationsByIndex.get(index);
        if (registration == null) {
            return;
        }
        Object message = registration.reader().apply(buf);
        dispatch(registration, message, player, NetworkDirection.PLAY_TO_SERVER);
    }

    private synchronized void ensureServerReceiver() {
        if (serverReceiverRegistered) {
            return;
        }
        serverReceiverRegistered = true;
        ServerPlayNetworking.registerGlobalReceiver(id, (server, player, handler, buf, responseSender) -> {
            FriendlyByteBuf payload = PacketByteBufs.copy(buf);
            server.execute(() -> handleServerbound(player, payload));
        });
    }

    private synchronized void ensureClientReceiver() {
        if (clientReceiverRegistered || FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return;
        }
        clientReceiverRegistered = true;
        ClientAccess.registerReceiver(this);
    }

    @SuppressWarnings("unchecked")
    private <MSG> Registration<MSG> getRegistration(MSG message) {
        Registration<?> registration = registrationsByType.get(message.getClass());
        if (registration == null) {
            throw new IllegalArgumentException("Unregistered packet type: " + message.getClass().getName());
        }
        return (Registration<MSG>) registration;
    }

    private record Registration<MSG>(
        int index,
        Class<MSG> type,
        BiConsumer<MSG, FriendlyByteBuf> writer,
        Function<FriendlyByteBuf, MSG> reader,
        BiConsumer<MSG, Supplier<NetworkEvent.Context>> handler
    ) {
    }

    private static <MSG> void dispatch(Registration<MSG> registration, Object message, ServerPlayer player, NetworkDirection direction) {
        registration.handler().accept(registration.type().cast(message), () -> new NetworkEvent.Context(player, direction));
    }

    private static final class ClientAccess {
        private ClientAccess() {
        }

        private static void registerReceiver(SimpleChannel channel) {
            ClientPlayNetworking.registerGlobalReceiver(channel.id, (client, handler, buf, responseSender) -> {
                FriendlyByteBuf payload = PacketByteBufs.copy(buf);
                client.execute(() -> channel.handleClientbound(payload));
            });
        }

        private static void sendToServer(ResourceLocation id, FriendlyByteBuf buf) {
            ClientPlayNetworking.send(id, buf);
        }
    }
}

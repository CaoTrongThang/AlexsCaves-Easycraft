package com.github.alexthe666.citadel.server.message;

import com.github.alexthe666.citadel.client.render.pathfinding.PathfindingDebugRenderer;
import com.github.alexthe666.citadel.server.entity.pathfinding.raycoms.MNode;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import com.github.alexmodguy.alexscaves.forge_shim.network.NetworkDirection;
import com.github.alexmodguy.alexscaves.forge_shim.network.NetworkEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class SyncPathReachedMessage {
    public Set<BlockPos> reached = new HashSet<>();

    public SyncPathReachedMessage(Set<BlockPos> reached) {
        this.reached = reached;
    }

    public void write(final FriendlyByteBuf buf) {
        buf.writeInt(reached.size());
        for (final BlockPos node : reached) {
            buf.writeBlockPos(node);
        }
    }

    public static SyncPathReachedMessage read(final FriendlyByteBuf buf) {
        int size = buf.readInt();
        Set<BlockPos> reached = new HashSet<>();
        for (int i = 0; i < size; i++) {
            reached.add(buf.readBlockPos());
        }
        return new SyncPathReachedMessage(reached);
    }

    public static class Handler {
        public static boolean handle(SyncPathReachedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            contextSupplier.get().enqueueWork(() -> {
                contextSupplier.get().setPacketHandled(true);
                if (contextSupplier.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                    for (final MNode node : PathfindingDebugRenderer.lastDebugNodesPath) {
                        if (message.reached.contains(node.pos)) {
                            node.setReachedByWorker(true);
                        }
                    }
                }
            });
            return true;
        }
    }
}

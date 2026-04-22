package net.neoforged.neoforge.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PacketDistributor {

    public static void sendToServer(Object payload) {
        com.github.alexmodguy.alexscaves.AlexsCaves.sendMSGToServer(payload);
    }

    public static void sendToPlayer(ServerPlayer player, Object payload) {
        com.github.alexmodguy.alexscaves.AlexsCaves.sendNonLocal(payload, player);
    }

    public static void sendToAllPlayers(Object payload) {
        com.github.alexmodguy.alexscaves.AlexsCaves.sendMSGToAll(payload);
    }

    public static void sendToPlayersTrackingEntityAndSelf(Entity entity, Object payload) {
        if (entity.level().isClientSide) {
            return;
        }
        com.github.alexmodguy.alexscaves.AlexsCaves.sendMSGToAll(payload);
    }

    private PacketDistributor() {
    }
}

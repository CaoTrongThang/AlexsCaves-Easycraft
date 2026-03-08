package net.neoforged.neoforge.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PacketDistributor {

    public static void sendToServer(CustomPacketPayload payload) {
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
    }

    public static void sendToAllPlayers(CustomPacketPayload payload) {
    }

    public static void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload) {
    }

    private PacketDistributor() {
    }
}

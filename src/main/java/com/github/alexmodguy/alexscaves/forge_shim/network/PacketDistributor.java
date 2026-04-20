package com.github.alexmodguy.alexscaves.forge_shim.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PacketDistributor {

    public static void sendToServer(Object payload) {
    }

    public static void sendToPlayer(ServerPlayer player, Object payload) {
    }

    public static void sendToAllPlayers(Object payload) {
    }

    public static void sendToPlayersTrackingEntityAndSelf(Entity entity, Object payload) {
    }

    private PacketDistributor() {
    }
}

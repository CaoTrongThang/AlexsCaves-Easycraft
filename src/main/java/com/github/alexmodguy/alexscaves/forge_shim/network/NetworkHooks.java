package com.github.alexmodguy.alexscaves.forge_shim.network;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;

public final class NetworkHooks {
    private NetworkHooks() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> Packet<ClientGamePacketListener> getEntitySpawningPacket(T entity) {
        return (Packet<ClientGamePacketListener>) new ClientboundAddEntityPacket(entity);
    }
}

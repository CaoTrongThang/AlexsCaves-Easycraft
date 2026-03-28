package com.github.alexthe666.citadel;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Citadel {
    public static final Logger LOGGER = LogManager.getLogger("citadel");
    public static final Proxy PROXY = new Proxy();

    public static <MSG> void sendMSGToServer(MSG message) {
    }

    public static <MSG> void sendMSGToAll(MSG message) {
    }

    public static <MSG> void sendNonLocal(MSG message, ServerPlayer player) {
    }

    public static class Proxy {
        public void handleAnimationPacket(int entityId, int index) {
        }

        public void handleClientTickRatePacket(CompoundTag compoundTag) {
        }

        public void handlePropertiesPacket(String propertyId, CompoundTag compoundTag, int entityId) {
        }

        public Player getClientSidePlayer() {
            return null;
        }

        public void handleJukeboxPacket(Level level, int entityId, BlockPos jukebox, boolean dance) {
        }
    }
}

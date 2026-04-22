package com.github.alexthe666.citadel;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Citadel {
    public static final Logger LOGGER = LogManager.getLogger("citadel");
    public static final Proxy PROXY = new Proxy();

    public static <MSG> void sendMSGToServer(MSG message) {
        com.github.alexmodguy.alexscaves.AlexsCaves.sendMSGToServer(message);
    }

    public static <MSG> void sendMSGToAll(MSG message) {
        com.github.alexmodguy.alexscaves.AlexsCaves.sendMSGToAll(message);
    }

    public static <MSG> void sendNonLocal(MSG message, ServerPlayer player) {
        com.github.alexmodguy.alexscaves.AlexsCaves.sendNonLocal(message, player);
    }

    public static class Proxy {
        public void handleAnimationPacket(int entityId, int index) {
            Player player = getClientSidePlayer();
            if (player != null && player.level() != null) {
                net.minecraft.world.entity.Entity entity = player.level().getEntity(entityId);
                if (entity instanceof com.github.alexthe666.citadel.animation.IAnimatedEntity animatedEntity) {
                    if (index == -1) {
                        animatedEntity
                                .setAnimation(com.github.alexthe666.citadel.animation.IAnimatedEntity.NO_ANIMATION);
                    } else {
                        com.github.alexthe666.citadel.animation.Animation[] animations = animatedEntity.getAnimations();
                        if (index >= 0 && index < animations.length) {
                            animatedEntity.setAnimation(animations[index]);
                        }
                    }
                    animatedEntity.setAnimationTick(0);
                }
            }
        }

        public void handleClientTickRatePacket(net.minecraft.nbt.CompoundTag compoundTag) {
            com.github.alexthe666.citadel.client.tick.ClientTickRateTracker
                    .getForClient(net.minecraft.client.Minecraft.getInstance()).syncFromServer(compoundTag);
        }

        public void handlePropertiesPacket(String propertyId, net.minecraft.nbt.CompoundTag compoundTag, int entityId) {
            Player player = getClientSidePlayer();
            if (player != null && player.level() != null) {
                net.minecraft.world.entity.Entity entity = player.level().getEntity(entityId);
                if (entity instanceof net.minecraft.world.entity.LivingEntity living) {
                    com.github.alexthe666.citadel.server.entity.CitadelEntityData.setCitadelTag(living, compoundTag);
                }
            }
        }

        public Player getClientSidePlayer() {
            return com.github.alexmodguy.alexscaves.AlexsCaves.PROXY.getClientSidePlayer();
        }

        public void handleJukeboxPacket(net.minecraft.world.level.Level level, int entityId,
                net.minecraft.core.BlockPos jukebox, boolean dance) {
            net.minecraft.world.entity.Entity entity = level.getEntity(entityId);
            if (entity instanceof net.minecraft.world.entity.LivingEntity living) {
                com.github.alexthe666.citadel.server.entity.CitadelEntityData.setBaseJukeboxPos(living,
                        dance ? jukebox : null);
            }
        }
    }
}

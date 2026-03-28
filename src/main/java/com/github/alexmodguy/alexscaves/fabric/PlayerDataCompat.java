package com.github.alexmodguy.alexscaves.fabric;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerDataCompat {
    public static final String PERSISTED_NBT_TAG = "PlayerPersisted";
    private static final Map<UUID, CompoundTag> PERSISTED_DATA = new ConcurrentHashMap<>();

    private PlayerDataCompat() {
    }

    public static CompoundTag getPersistentData(Player player) {
        return PERSISTED_DATA.computeIfAbsent(player.getUUID(), uuid -> new CompoundTag());
    }

    public static CompoundTag getPersistedTag(Player player) {
        CompoundTag playerData = getPersistentData(player);
        if (!playerData.contains(PERSISTED_NBT_TAG, 10)) {
            playerData.put(PERSISTED_NBT_TAG, new CompoundTag());
        }
        return playerData.getCompound(PERSISTED_NBT_TAG);
    }
}

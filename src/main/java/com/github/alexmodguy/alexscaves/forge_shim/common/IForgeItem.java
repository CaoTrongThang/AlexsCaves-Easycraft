package com.github.alexmodguy.alexscaves.forge_shim.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IForgeItem {
    default void onArmorTick(ItemStack stack, Level level, Player player) {
    }
}

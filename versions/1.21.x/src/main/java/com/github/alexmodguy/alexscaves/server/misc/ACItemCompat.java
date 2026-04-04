package com.github.alexmodguy.alexscaves.server.misc;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public final class ACItemCompat {

    private ACItemCompat() {
    }

    public static ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.getItem().getCraftingRemainder().copy();
    }

    public static boolean canPerformAction(ItemStack stack, ItemAbility action) {
        Item item = stack.getItem();
        if (action == ItemAbilities.AXE_STRIP || action == ItemAbilities.AXE_SCRAPE) {
            return item instanceof AxeItem;
        }
        if (action == ItemAbilities.SHIELD_BLOCK) {
            return item instanceof ShieldItem;
        }
        if (action == ItemAbilities.FISHING_ROD_CAST) {
            return item instanceof FishingRodItem;
        }
        return false;
    }
}

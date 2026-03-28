package com.github.alexmodguy.alexscaves.fabric;

import com.github.alexmodguy.alexscaves.server.item.BiomeTreatItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;

public final class ItemStackCompat {
    private ItemStackCompat() {
    }

    public static int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        return EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
    }

    public static FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (stack.getItem() instanceof BiomeTreatItem biomeTreatItem) {
            return biomeTreatItem.getFoodProperties(stack, entity);
        }
        return stack.getItem().getFoodProperties();
    }

    public static ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.getItem().hasCraftingRemainingItem() ? new ItemStack(stack.getItem().getCraftingRemainingItem()) : ItemStack.EMPTY;
    }
}

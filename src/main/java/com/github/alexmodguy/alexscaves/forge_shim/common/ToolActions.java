package com.github.alexmodguy.alexscaves.forge_shim.common;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

import java.lang.reflect.Method;

public final class ToolActions {
    public static final ToolAction AXE_STRIP = new ToolAction("axe_strip");
    public static final ToolAction AXE_SCRAPE = new ToolAction("axe_scrape");
    public static final ToolAction SHIELD_BLOCK = new ToolAction("shield_block");
    public static final ToolAction FISHING_ROD_CAST = new ToolAction("fishing_rod_cast");

    private ToolActions() {
    }

    public static boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        try {
            Method method = stack.getItem().getClass().getMethod("canPerformAction", ItemStack.class, ToolAction.class);
            if (method.getDeclaringClass() != net.minecraft.world.item.Item.class) {
                return (Boolean) method.invoke(stack.getItem(), stack, toolAction);
            }
        } catch (ReflectiveOperationException ignored) {
        }

        if (toolAction == AXE_STRIP || toolAction == AXE_SCRAPE) {
            return stack.getItem() instanceof AxeItem;
        }
        if (toolAction == SHIELD_BLOCK) {
            return stack.getItem() instanceof ShieldItem;
        }
        if (toolAction == FISHING_ROD_CAST) {
            return stack.getItem() instanceof FishingRodItem;
        }
        return false;
    }
}

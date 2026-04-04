package com.github.alexmodguy.alexscaves.client.particle.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ItemParticleCompat {

    private ItemParticleCompat() {
    }

    public static ItemStackRenderState createRenderState(ItemStack itemStack) {
        ItemStackRenderState renderState = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(renderState, itemStack, ItemDisplayContext.GROUND, Minecraft.getInstance().level, null, 0);
        return renderState;
    }
}

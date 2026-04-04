package com.github.alexmodguy.alexscaves.client.render.entity.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ItemInHandCompat {

    private ItemInHandCompat() {
    }

    public static void renderItem(ItemInHandRenderer renderer, LivingEntity livingEntity, ItemStack itemStack,
            ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource multiBufferSource,
            int packedLight) {
        renderer.renderItem(livingEntity, itemStack, displayContext, poseStack, multiBufferSource, packedLight);
    }
}

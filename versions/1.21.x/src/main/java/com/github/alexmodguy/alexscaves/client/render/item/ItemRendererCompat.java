package com.github.alexmodguy.alexscaves.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemRendererCompat {

    private ItemRendererCompat() {
    }

    public static BakedModel getModel(ItemRenderer renderer, ItemStack itemStack, Level level) {
        return null;
    }

    public static void render(ItemRenderer renderer, ItemStack itemStack, ItemDisplayContext displayContext,
            boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay,
            BakedModel bakedModel) {
        renderer.renderStatic(itemStack, displayContext, packedLight, packedOverlay, poseStack, bufferSource,
            Minecraft.getInstance().level, 0);
    }
}

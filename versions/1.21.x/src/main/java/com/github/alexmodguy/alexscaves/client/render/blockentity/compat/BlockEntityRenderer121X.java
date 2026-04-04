package com.github.alexmodguy.alexscaves.client.render.blockentity.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public interface BlockEntityRenderer121X<T extends BlockEntity> extends BlockEntityRenderer<T> {

    void render(T blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            int packedOverlay);

    @Override
    default void render(T blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource,
            int packedLight, int packedOverlay, Vec3 cameraPos) {
        render(blockEntity, partialTicks, poseStack, bufferSource, packedLight, packedOverlay);
    }
}

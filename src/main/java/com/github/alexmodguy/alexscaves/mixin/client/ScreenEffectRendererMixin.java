package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.fabric.FluidTypeCompat;
import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.github.alexmodguy.alexscaves.server.block.fluid.AcidFluidType;
import com.github.alexmodguy.alexscaves.server.block.fluid.PurpleSodaFluidType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @Inject(
            method = {"Lnet/minecraft/client/renderer/ScreenEffectRenderer;renderScreenEffect(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;)V"},
            remap = true,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isOnFire()Z",
                    shift = At.Shift.BEFORE
            )
    )
    private static void ac_renderFluidScreenOverlays(Minecraft minecraft, PoseStack poseStack, CallbackInfo ci) {
        LocalPlayer player = minecraft.player;
        if (player == null || player.isSpectator()) {
            return;
        }

        FluidType eyeFluidType = FluidTypeCompat.getEyeInFluidType(player);
        if (eyeFluidType == ACFluidRegistry.ACID_FLUID_TYPE.get()) {
            renderFluidOverlay(minecraft, poseStack, AcidFluidType.OVERLAY, 0.2F);
        } else if (eyeFluidType == ACFluidRegistry.PURPLE_SODA_FLUID_TYPE.get()) {
            renderFluidOverlay(minecraft, poseStack, PurpleSodaFluidType.OVERLAY, 0.2F);
        }
    }

    private static void renderFluidOverlay(Minecraft minecraft, PoseStack poseStack, ResourceLocation texture, float alpha) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        BlockPos blockPos = BlockPos.containing(minecraft.player.getX(), minecraft.player.getEyeY(), minecraft.player.getZ());
        float brightness = LightTexture.getBrightness(minecraft.player.level().dimensionType(), minecraft.player.level().getMaxLocalRawBrightness(blockPos));
        float yawOffset = -minecraft.player.getYRot() / 64.0F;
        float pitchOffset = minecraft.player.getXRot() / 64.0F;
        Matrix4f matrix4f = poseStack.last().pose();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(brightness, brightness, brightness, alpha);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).uv(4.0F + yawOffset, 4.0F + pitchOffset).endVertex();
        bufferBuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).uv(0.0F + yawOffset, 4.0F + pitchOffset).endVertex();
        bufferBuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).uv(0.0F + yawOffset, 0.0F + pitchOffset).endVertex();
        bufferBuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).uv(4.0F + yawOffset, 0.0F + pitchOffset).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}

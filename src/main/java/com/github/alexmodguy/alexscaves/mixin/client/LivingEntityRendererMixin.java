package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.client.render.entity.LivingEntityRendererAccessor;
import com.github.alexmodguy.alexscaves.forge_shim.client.event.RenderLivingEvent;
import com.github.alexmodguy.alexscaves.forge_shim.common.MinecraftForge;
import com.github.alexthe666.citadel.client.event.EventLivingRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer implements LivingEntityRendererAccessor {

    @Shadow
    protected abstract void scale(LivingEntity living, PoseStack poseStack, float f);

    @Shadow
    protected abstract boolean addLayer(RenderLayer<?, ?> renderLayer);

    @Shadow
    protected EntityModel model;

    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void ac_beforeRender(LivingEntity entity, float f1, float f2, PoseStack poseStack,
            MultiBufferSource bufferSource, int i, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Pre(entity, (LivingEntityRenderer) (Object) this, f2,
                poseStack, bufferSource, i))) {
            ci.cancel();
        }
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("TAIL"))
    private void ac_afterRender(LivingEntity entity, float f1, float f2, PoseStack poseStack,
            MultiBufferSource bufferSource, int i, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(entity, (LivingEntityRenderer) (Object) this, f2,
                poseStack, bufferSource, i));
    }

    @Inject(method = "setupRotations(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At("TAIL"))
    private void ac_setupRotations(LivingEntity entity, PoseStack poseStack, float ageInTicks, float bodyYRot,
            float partialTick, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS
                .post(new EventLivingRenderer.SetupRotations(entity, this.model, poseStack, bodyYRot, partialTick));
    }

    @Override
    public void scaleForHologram(LivingEntity entity, PoseStack poseStack, float partialTicks) {
        this.scale(entity, poseStack, partialTicks);
    }

    @Override
    public void addACLayer(RenderLayer<?, ?> layer) {
        this.addLayer(layer);
    }
}

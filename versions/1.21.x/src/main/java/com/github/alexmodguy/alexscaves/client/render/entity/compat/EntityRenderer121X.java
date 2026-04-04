package com.github.alexmodguy.alexscaves.client.render.entity.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public abstract class EntityRenderer121X<T extends Entity>
        extends net.minecraft.client.renderer.entity.EntityRenderer<T, EntityRenderState> {
    protected T currentEntity;
    protected float currentEntityYaw;
    protected float currentPartialTicks;

    protected EntityRenderer121X(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void extractRenderState(T entity, EntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        this.currentEntity = entity;
        this.currentPartialTicks = partialTicks;
        this.currentEntityYaw = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
    }

    @Override
    public final void render(EntityRenderState state, PoseStack poseStack, MultiBufferSource bufferSource,
            int packedLight) {
        if (currentEntity != null) {
            this.render(currentEntity, currentEntityYaw, currentPartialTicks, poseStack, bufferSource, packedLight);
        }
    }

    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource source,
            int packedLight) {
    }

    public void render(T entity, double x, double y, double z, float entityYaw, float partialTicks,
            PoseStack poseStack, MultiBufferSource source, int packedLight) {
        this.render(entity, entityYaw, partialTicks, poseStack, source, packedLight);
    }

    public abstract ResourceLocation getTextureLocation(T entity);
}

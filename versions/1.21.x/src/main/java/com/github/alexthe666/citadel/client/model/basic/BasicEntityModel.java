package com.github.alexthe666.citadel.client.model.basic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

public abstract class BasicEntityModel<T extends Entity> {
    public int textureWidth = 64;
    public int textureHeight = 32;
    public float attackTime;
    public boolean riding;
    public boolean young;
    private final Function<ResourceLocation, RenderType> renderType;

    protected BasicEntityModel() {
        this(RenderType::entityCutoutNoCull);
    }

    protected BasicEntityModel(Function<ResourceLocation, RenderType> renderType) {
        this.renderType = renderType;
    }

    public RenderType renderType(ResourceLocation texture) {
        return renderType.apply(texture);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn,
            int color) {
        this.parts().forEach(part -> part.render(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, color));
    }

    public abstract Iterable<BasicModelPart> parts();

    public abstract void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch);

    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
    }

    public void copyPropertiesTo(BasicEntityModel<T> model) {
        model.attackTime = this.attackTime;
        model.riding = this.riding;
        model.young = this.young;
    }
}

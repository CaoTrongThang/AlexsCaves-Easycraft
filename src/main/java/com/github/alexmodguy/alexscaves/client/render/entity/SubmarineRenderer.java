package com.github.alexmodguy.alexscaves.client.render.entity;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.model.SubmarineModel;
import com.github.alexmodguy.alexscaves.client.render.ACRenderTypes;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class SubmarineRenderer extends EntityRenderer<SubmarineEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine.png");
    private static final ResourceLocation TEXTURE_EXPOSED = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_exposed.png");
    private static final ResourceLocation TEXTURE_WEATHERED = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_weathered.png");
    private static final ResourceLocation TEXTURE_OXIDIZED = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_oxidized.png");
    private static final ResourceLocation TEXTURE_NEW = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_new.png");
    private static final ResourceLocation TEXTURE_LOW = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_low.png");
    private static final ResourceLocation TEXTURE_MEDIUM = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_medium.png");
    private static final ResourceLocation TEXTURE_HIGH = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_high.png");
    private static final ResourceLocation TEXTURE_CRITICAL = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_critical.png");
    private static final ResourceLocation TEXTURE_BUTTONS = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_buttons.png");
    private static final ResourceLocation TEXTURE_GLOW = new ResourceLocation(AlexsCaves.MODID,
            "textures/entity/submarine/submarine_glow.png");
    private static SubmarineModel MODEL = new SubmarineModel();

    public SubmarineRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 1.0F;
    }

    public void render(SubmarineEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource source, int lightIn) {
        if (!isFirstPersonFloodlightsMode(entity)) {
            renderSubmarine(entity, partialTicks, poseStack, source, lightIn, true);
            super.render(entity, entityYaw, partialTicks, poseStack, source, lightIn);
        }
    }

    public static boolean isFirstPersonFloodlightsMode(SubmarineEntity entity) {
        Entity player = Minecraft.getInstance().getCameraEntity();
        return player.isPassengerOfSameVehicle(entity)
                && Minecraft.getInstance().options.getCameraType().isFirstPerson() && entity.areLightsOn();
    }

    public static void renderSubFirstPerson(SubmarineEntity entity, float partialTicks, PoseStack poseStack,
            MultiBufferSource source) {
        renderSubmarine(entity, partialTicks, poseStack, source,
                LevelRenderer.getLightColor(entity.level(), entity.blockPosition()), false);
    }

    public static void renderSubmarine(SubmarineEntity entity, float partialTicks, PoseStack poseStack,
            MultiBufferSource source, int lightIn, boolean maskWater) {
        Player player = Minecraft.getInstance().player;
        float ageInTicks = entity.tickCount + partialTicks;
        float submarineYaw = entity.getViewYRot(partialTicks);
        float submarinePitch = entity.getViewXRot(partialTicks);
        poseStack.pushPose();
        poseStack.translate(0.0D, 1.5D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - submarineYaw));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.XN.rotationDegrees(submarinePitch));
        poseStack.translate(0, 0, 0);
        if (entity.getWaterHeight() > 0 && entity.getWaterHeight() < 1.6F) {
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (Math.sin(ageInTicks * 0.1F) * 0.5F)));
            poseStack.mulPose(Axis.XP.rotationDegrees((float) (Math.sin(ageInTicks * 0.1F + 1.3F) * 0.5F)));
        }
        poseStack.pushPose();
        MODEL.setupAnim(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
        for (Entity passenger : entity.getPassengers()) {
            if (passenger == player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                continue;
            }
            AlexsCaves.PROXY.releaseRenderingEntity(passenger.getUUID());
            poseStack.pushPose();
            poseStack.translate(0, 0.65F, -0.75F);
            poseStack.mulPose(Axis.XN.rotationDegrees(180F));
            poseStack.mulPose(Axis.YN.rotationDegrees(360 - submarineYaw));
            renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, source, lightIn);
            poseStack.popPose();
            AlexsCaves.PROXY.blockRenderingEntity(passenger.getUUID());
        }
        VertexConsumer textureBuffer = source.getBuffer(RenderType.entityCutoutNoCull(getSubmarineBaseTexture(entity)));
        MODEL.renderToBuffer(poseStack, textureBuffer, lightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        VertexConsumer damageBuffer = source.getBuffer(RenderType.entityTranslucent(getSubmarineDamageTexture(entity)));
        MODEL.renderToBuffer(poseStack, damageBuffer, lightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (entity.getDamageLevel() <= 3) {
            VertexConsumer buttonsBuffer = source.getBuffer(ACRenderTypes.getEyesAlphaEnabled(TEXTURE_BUTTONS));
            MODEL.renderToBuffer(poseStack, buttonsBuffer, lightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
                    entity.getSonarFlashAmount(partialTicks));
            if (entity.areLightsOn() && entity.isVehicle()) {
                VertexConsumer glowBuffer = source.getBuffer(RenderType.eyes(TEXTURE_GLOW));
                MODEL.renderToBuffer(poseStack, glowBuffer, lightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        if (maskWater) {
            VertexConsumer waterMask = source.getBuffer(ACRenderTypes.getSubmarineMask());
            MODEL.setupWaterMask(entity, partialTicks);
            MODEL.getWaterMask().render(poseStack, waterMask, lightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
                    1.0F);
        }

        poseStack.popPose();
        poseStack.popPose();
    }

    private static ResourceLocation getSubmarineDamageTexture(SubmarineEntity entity) {
        switch (entity.getDamageLevel()) {
            case 0:
                return TEXTURE_NEW;
            case 1:
                return TEXTURE_LOW;
            case 2:
                return TEXTURE_MEDIUM;
            case 3:
                return TEXTURE_HIGH;
            case 4:
                return TEXTURE_CRITICAL;
        }
        return TEXTURE_NEW;
    }

    public static <E extends Entity> void renderPassenger(E entityIn, double x, double y, double z, float yaw,
            float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        EntityRenderer<? super E> render = null;
        EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();
        try {
            render = manager.getRenderer(entityIn);

            if (render != null) {
                try {
                    render.render(entityIn, yaw, partialTicks, matrixStack, bufferIn, packedLight);
                } catch (Throwable throwable1) {
                    throw new ReportedException(CrashReport.forThrowable(throwable1, "Rendering entity in world"));
                }
            }
        } catch (Throwable throwable3) {
            CrashReport crashreport = CrashReport.forThrowable(throwable3, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Entity being rendered");
            entityIn.fillCrashReportCategory(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.addCategory("Renderer details");
            crashreportcategory1.setDetail("Assigned renderer", render);
            crashreportcategory1.setDetail("Rotation", Float.valueOf(yaw));
            crashreportcategory1.setDetail("Delta", Float.valueOf(partialTicks));
            throw new ReportedException(crashreport);
        }
    }

    private static ResourceLocation getSubmarineBaseTexture(SubmarineEntity entity) {
        switch (entity.getOxidizationLevel()) {
            case 0:
                return TEXTURE;
            case 1:
                return TEXTURE_EXPOSED;
            case 2:
                return TEXTURE_WEATHERED;
            case 3:
                return TEXTURE_OXIDIZED;
        }
        return TEXTURE;
    }

    public ResourceLocation getTextureLocation(SubmarineEntity entity) {
        return TEXTURE;
    }
}

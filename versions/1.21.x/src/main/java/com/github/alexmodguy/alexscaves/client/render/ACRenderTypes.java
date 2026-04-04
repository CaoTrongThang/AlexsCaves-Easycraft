package com.github.alexmodguy.alexscaves.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class ACRenderTypes {
    private ACRenderTypes() {
    }

    public static RenderType entityTranslucent(ResourceLocation location) {
        return RenderType.entityTranslucent(location);
    }

    public static RenderType getParticleTrail(ResourceLocation resourceLocation) {
        return RenderType.entityTranslucent(resourceLocation);
    }

    public static RenderType getVoidBeingCloud(ResourceLocation resourceLocation) {
        return RenderType.entityTranslucent(resourceLocation);
    }

    public static RenderType getEyesAlphaEnabled(ResourceLocation locationIn) {
        return RenderType.eyes(locationIn);
    }

    public static RenderType getAmbersolShine() {
        return RenderType.lightning();
    }

    public static RenderType getNucleeperLights() {
        return RenderType.lightning();
    }

    public static RenderType getHologramLights() {
        return RenderType.lightning();
    }

    public static RenderType getCrucibleItemBeam() {
        return RenderType.lightning();
    }

    public static RenderType getSubmarineLights() {
        return RenderType.lightning();
    }

    public static RenderType getGel(ResourceLocation locationIn) {
        return RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getRadiationGlow(ResourceLocation locationIn) {
        return RenderType.entityTranslucentEmissive(locationIn);
    }

    public static RenderType getBlueRadiationGlow(ResourceLocation locationIn) {
        return RenderType.entityTranslucentEmissive(locationIn);
    }

    public static RenderType getGelTriangles(ResourceLocation locationIn) {
        return RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getSubmarineMask() {
        return RenderType.waterMask();
    }

    public static RenderType getGhostly(ResourceLocation texture) {
        return RenderType.entityTranslucentEmissive(texture);
    }

    public static RenderType getTeslaBulb(ResourceLocation resourceLocation) {
        return RenderType.entityTranslucentEmissive(resourceLocation);
    }

    public static RenderType getRainbow(ResourceLocation resourceLocation) {
        return RenderType.entityTranslucent(resourceLocation);
    }

    public static RenderType getHologram(ResourceLocation locationIn) {
        return RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getRedGhost(ResourceLocation locationIn) {
        return RenderType.entityTranslucentEmissive(locationIn);
    }

    public static RenderType getCaveMapBackground(ResourceLocation locationIn, boolean showBackground) {
        return RenderType.entityCutoutNoCull(locationIn);
    }

    public static RenderType getBookWidget(ResourceLocation locationIn, boolean sepia) {
        return RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getBubbledCull(ResourceLocation locationIn) {
        return RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getBubbledNoCull(ResourceLocation locationIn) {
        return RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getRaygunRay(ResourceLocation locationIn, boolean irradiated) {
        return irradiated ? RenderType.entityTranslucentEmissive(locationIn) : RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getTremorzillaBeam(ResourceLocation locationIn, boolean irradiated) {
        return irradiated ? RenderType.entityTranslucentEmissive(locationIn) : RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getPurpleWitch(ResourceLocation locationIn) {
        return RenderType.entityTranslucent(locationIn);
    }

    public static RenderType getWatcherAppearance(ResourceLocation locationIn) {
        return RenderType.entityTranslucent(locationIn);
    }
}

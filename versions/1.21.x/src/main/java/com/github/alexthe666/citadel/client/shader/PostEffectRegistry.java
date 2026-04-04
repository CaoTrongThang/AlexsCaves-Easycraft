package com.github.alexthe666.citadel.client.shader;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashSet;
import java.util.Set;

public class PostEffectRegistry {

    private static final Set<ResourceLocation> REGISTRY = new LinkedHashSet<>();

    public static void clear() {
    }

    public static void registerEffect(ResourceLocation resourceLocation) {
        REGISTRY.add(resourceLocation);
    }

    public static void onInitializeOutline() {
    }

    public static void ensureInitialized() {
    }

    public static void beginFrame(RenderTarget mainTarget) {
    }

    public static void resize(int x, int y) {
    }

    public static RenderTarget getRenderTargetFor(ResourceLocation resourceLocation) {
        return null;
    }

    public static void renderEffectForNextTick(ResourceLocation resourceLocation) {
    }

    public static void blitEffects() {
    }

    public static void clearAndBindWrite(RenderTarget mainTarget) {
    }

    public static void processEffects(RenderTarget mainTarget) {
    }
}

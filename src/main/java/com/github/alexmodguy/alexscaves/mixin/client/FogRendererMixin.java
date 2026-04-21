package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.forge_shim.common.MinecraftForge;
import com.github.alexmodguy.alexscaves.forge_shim.client.event.ViewportEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {

    @Inject(method = "Lnet/minecraft/client/renderer/FogRenderer;setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", remap = true, at = @At("TAIL"))
    private static void ac_setupFog(Camera camera, FogRenderer.FogMode mode, float renderDistance, boolean isFoggy,
            float partialTick, CallbackInfo ci) {
        float near = RenderSystem.getShaderFogStart();
        float far = RenderSystem.getShaderFogEnd();
        ViewportEvent.RenderFog event = new ViewportEvent.RenderFog(camera, mode, partialTick, near, far);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            RenderSystem.setShaderFogStart(event.getNearPlaneDistance());
            RenderSystem.setShaderFogEnd(event.getFarPlaneDistance());
        }
    }

    @Inject(method = "Lnet/minecraft/client/renderer/FogRenderer;setupColor(Lnet/minecraft/client/Camera;FLnet/minecraft/client/multiplayer/ClientLevel;IF)V", remap = true, at = @At("TAIL"))
    private static void ac_setupColor(Camera camera, float partialTick, ClientLevel level, int renderDistance,
            float darkenWorldAmount, CallbackInfo ci) {
        float[] fogColor = RenderSystem.getShaderFogColor();
        ViewportEvent.ComputeFogColor event = new ViewportEvent.ComputeFogColor(camera, partialTick, fogColor[0],
                fogColor[1], fogColor[2]);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getRed() != fogColor[0] || event.getGreen() != fogColor[1] || event.getBlue() != fogColor[2]) {
            RenderSystem.setShaderFogColor(event.getRed(), event.getGreen(), event.getBlue(), fogColor[3]);
        }
    }
}

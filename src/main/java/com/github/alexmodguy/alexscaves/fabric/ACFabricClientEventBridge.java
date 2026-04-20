package com.github.alexmodguy.alexscaves.fabric;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import com.github.alexmodguy.alexscaves.forge_shim.common.MinecraftForge;
import com.github.alexmodguy.alexscaves.forge_shim.event.TickEvent;
import com.github.alexmodguy.alexscaves.forge_shim.client.event.RenderLevelStageEvent;

public final class ACFabricClientEventBridge {

    private ACFabricClientEventBridge() {
    }

    public static void registerClient() {
        ClientTickEvents.START_CLIENT_TICK.register(
                client -> MinecraftForge.EVENT_BUS.post(new TickEvent.ClientTickEvent(TickEvent.Phase.START)));
        ClientTickEvents.END_CLIENT_TICK
                .register(client -> MinecraftForge.EVENT_BUS.post(new TickEvent.ClientTickEvent(TickEvent.Phase.END)));
        WorldRenderEvents.START.register(context -> {
            com.github.alexthe666.citadel.client.shader.PostEffectRegistry
                    .beginFrame(net.minecraft.client.Minecraft.getInstance().getMainRenderTarget());
        });
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftForge.EVENT_BUS.post(new RenderLevelStageEvent(RenderLevelStageEvent.Stage.AFTER_ENTITIES,
                    context.worldRenderer(), context.matrixStack(), 0, context.camera(), context.tickDelta()));
        });
        WorldRenderEvents.END.register(context -> {
            com.github.alexthe666.citadel.client.shader.PostEffectRegistry
                    .processEffects(net.minecraft.client.Minecraft.getInstance().getMainRenderTarget());
            com.github.alexthe666.citadel.client.shader.PostEffectRegistry.blitEffects();
        });
    }
}

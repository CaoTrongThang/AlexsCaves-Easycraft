package com.github.alexmodguy.alexscaves.forge_shim.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class ForgeRenderTypes {
    private ForgeRenderTypes() {
    }

    public static RenderType getUnlitTranslucent(ResourceLocation texture) {
        return NeoForgeRenderTypes.getUnlitTranslucent(texture);
    }
}

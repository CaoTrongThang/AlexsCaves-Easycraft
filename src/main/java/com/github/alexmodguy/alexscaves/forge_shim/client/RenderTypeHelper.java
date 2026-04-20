package com.github.alexmodguy.alexscaves.forge_shim.client;

import net.minecraft.client.renderer.RenderType;

public final class RenderTypeHelper {
    private RenderTypeHelper() {
    }

    public static RenderType getEntityRenderType(RenderType renderType, boolean translucent) {
        return renderType;
    }
}

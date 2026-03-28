package net.minecraftforge.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class NeoForgeRenderTypes {
    private NeoForgeRenderTypes() {
    }

    public static RenderType getUnlitTranslucent(ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}

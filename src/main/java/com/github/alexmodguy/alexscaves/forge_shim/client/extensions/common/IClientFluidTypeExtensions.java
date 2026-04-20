package com.github.alexmodguy.alexscaves.forge_shim.client.extensions.common;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public interface IClientFluidTypeExtensions {

    default ResourceLocation getStillTexture() {
        return null;
    }

    default ResourceLocation getFlowingTexture() {
        return null;
    }

    default ResourceLocation getRenderOverlayTexture(Minecraft minecraft) {
        return null;
    }
}

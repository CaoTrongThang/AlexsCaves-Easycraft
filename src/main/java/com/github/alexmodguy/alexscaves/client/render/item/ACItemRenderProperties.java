package com.github.alexmodguy.alexscaves.client.render.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import com.github.alexmodguy.alexscaves.forge_shim.client.extensions.common.IClientItemExtensions;

public class ACItemRenderProperties implements IClientItemExtensions {

    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new ACItemstackRenderer();
    }
}

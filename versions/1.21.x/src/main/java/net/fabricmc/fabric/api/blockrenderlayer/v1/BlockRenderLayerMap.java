package net.fabricmc.fabric.api.blockrenderlayer.v1;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class BlockRenderLayerMap {
    public static final BlockRenderLayerMap INSTANCE = new BlockRenderLayerMap();

    public void putBlock(Block block, RenderType renderType) {
    }

    public void putFluids(RenderType renderType, Fluid... fluids) {
    }
}

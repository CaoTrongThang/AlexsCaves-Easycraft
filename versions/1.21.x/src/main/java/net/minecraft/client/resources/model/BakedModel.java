package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public interface BakedModel {
    List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource random);

    boolean useAmbientOcclusion();

    boolean isGui3d();

    boolean usesBlockLight();

    boolean isCustomRenderer();

    TextureAtlasSprite getParticleIcon();

    ItemTransforms getTransforms();

    ItemOverrides getOverrides();
}

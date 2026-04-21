package com.github.alexmodguy.alexscaves.server.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LightSourceBlockEntity extends BlockEntity {

    private int lifespan = 5;

    public LightSourceBlockEntity(BlockPos pos, BlockState state) {
        super(ACBlockEntityRegistry.LIGHT_SOURCE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LightSourceBlockEntity entity) {
        if (entity.lifespan > 0) {
            entity.lifespan--;
        } else {
            // AlexsCaves.LOGGER.info("SERVER: Light source at " + pos + " expired");
            level.setBlockAndUpdate(pos, state.getFluidState().createLegacyBlock());
        }
    }

    public void refresh() {
        this.lifespan = 5;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.lifespan = tag.getInt("Lifespan");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Lifespan", this.lifespan);
    }
}

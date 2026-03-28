package com.github.alexmodguy.alexscaves.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.BaseFlowingFluid;
import net.minecraftforge.fluids.FluidType;

public final class FluidTypeCompat {
    private FluidTypeCompat() {
    }

    public static FluidType getFluidType(FluidState state) {
        return state == null ? null : getFluidType(state.getType());
    }

    public static FluidType getFluidType(Fluid fluid) {
        if (fluid instanceof BaseFlowingFluid flowingFluid) {
            return flowingFluid.getFluidType();
        }
        if (fluid.isSame(Fluids.WATER)) {
            return ForgeMod.WATER_TYPE.get();
        }
        if (fluid.isSame(Fluids.LAVA)) {
            return ForgeMod.LAVA_TYPE.get();
        }
        return null;
    }

    public static FluidType getEyeInFluidType(Entity entity) {
        FluidState state = entity.level().getFluidState(BlockPos.containing(entity.getEyePosition()));
        if (!state.isEmpty()) {
            return getFluidType(state);
        }
        if (entity.isEyeInFluid(FluidTags.WATER)) {
            return ForgeMod.WATER_TYPE.get();
        }
        if (entity.isEyeInFluid(FluidTags.LAVA)) {
            return ForgeMod.LAVA_TYPE.get();
        }
        return null;
    }

    public static boolean isInFluidType(Entity entity) {
        return !entity.level().getFluidState(entity.blockPosition()).isEmpty() || entity.isInWaterOrBubble();
    }
}

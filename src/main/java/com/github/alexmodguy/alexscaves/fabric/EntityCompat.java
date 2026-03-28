package com.github.alexmodguy.alexscaves.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

public final class EntityCompat {
    private EntityCompat() {
    }

    public static float getStepHeight(Entity entity) {
        return entity.maxUpStep();
    }

    public static boolean isInFluidType(Entity entity) {
        return FluidTypeCompat.isInFluidType(entity);
    }

    public static double getFluidTypeHeight(Entity entity, FluidType fluidType) {
        if (fluidType == ForgeMod.WATER_TYPE.get()) {
            return entity.getFluidHeight(FluidTags.WATER);
        }
        if (fluidType == ForgeMod.LAVA_TYPE.get()) {
            return entity.getFluidHeight(FluidTags.LAVA);
        }
        FluidState stateAtFeet = entity.level().getFluidState(entity.blockPosition());
        if (FluidTypeCompat.getFluidType(stateAtFeet) == fluidType) {
            return stateAtFeet.getHeight(entity.level(), entity.blockPosition());
        }
        BlockPos eyePos = BlockPos.containing(entity.getEyePosition());
        FluidState stateAtEyes = entity.level().getFluidState(eyePos);
        if (FluidTypeCompat.getFluidType(stateAtEyes) == fluidType) {
            return stateAtEyes.getHeight(entity.level(), eyePos);
        }
        return 0.0D;
    }

    public static boolean shouldRiderSit(Entity entity) {
        try {
            return (Boolean) entity.getClass().getMethod("shouldRiderSit").invoke(entity);
        } catch (ReflectiveOperationException ignored) {
            return true;
        }
    }
}

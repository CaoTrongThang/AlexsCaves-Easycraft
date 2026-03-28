package net.minecraftforge.fluids;

import net.minecraft.world.level.material.Fluid;

public record FluidStack(Fluid fluid, int amount) {
}

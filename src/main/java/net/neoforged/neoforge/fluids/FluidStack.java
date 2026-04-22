package net.neoforged.neoforge.fluids;

import net.minecraft.world.level.material.Fluid;

public record FluidStack(Fluid fluid, int amount) {
}

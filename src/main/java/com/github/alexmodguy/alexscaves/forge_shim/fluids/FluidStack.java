package com.github.alexmodguy.alexscaves.forge_shim.fluids;

import net.minecraft.world.level.material.Fluid;

public record FluidStack(Fluid fluid, int amount) {
}

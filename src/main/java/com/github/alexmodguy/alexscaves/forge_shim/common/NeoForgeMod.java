package com.github.alexmodguy.alexscaves.forge_shim.common;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import com.github.alexmodguy.alexscaves.forge_shim.fluids.FluidType;

public final class NeoForgeMod {
    public static final Holder<Attribute> SWIM_SPEED = Holder.direct(ForgeMod.SWIM_SPEED.get());
    public static final Holder<FluidType> WATER_TYPE = Holder.direct(new FluidType(FluidType.Properties.create()));
    public static final Holder<FluidType> LAVA_TYPE = Holder.direct(new FluidType(FluidType.Properties.create()));

    private NeoForgeMod() {
    }
}

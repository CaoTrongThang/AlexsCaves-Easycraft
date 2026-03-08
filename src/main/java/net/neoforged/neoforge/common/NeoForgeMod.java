package net.neoforged.neoforge.common;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.fluids.FluidType;

public final class NeoForgeMod {
    public static final Holder<Attribute> SWIM_SPEED = Attributes.MOVEMENT_SPEED;
    public static final Holder<FluidType> WATER_TYPE = Holder.direct(new FluidType(FluidType.Properties.create()));
    public static final Holder<FluidType> LAVA_TYPE = Holder.direct(new FluidType(FluidType.Properties.create()));

    private NeoForgeMod() {
    }
}

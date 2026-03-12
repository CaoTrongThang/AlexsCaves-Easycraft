package net.neoforged.neoforge.common;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.fluids.FluidType;

public final class NeoForgeMod {
    // Reuse the vanilla swim attribute so all living entities already carry the stat on Fabric.
    public static final Holder<Attribute> SWIM_SPEED = Attributes.WATER_MOVEMENT_EFFICIENCY;
    public static final Holder<FluidType> WATER_TYPE = Holder.direct(new FluidType(FluidType.Properties.create()));
    public static final Holder<FluidType> LAVA_TYPE = Holder.direct(new FluidType(FluidType.Properties.create()));

    private NeoForgeMod() {
    }
}

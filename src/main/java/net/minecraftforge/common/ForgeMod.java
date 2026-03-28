package net.minecraftforge.common;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fluids.FluidType;

public final class ForgeMod {
    public static final SimpleHolder<Attribute> SWIM_SPEED = new SimpleHolder<>(new RangedAttribute("forge.swim_speed", 1.0D, 0.0D, 1024.0D));
    public static final SimpleHolder<Attribute> ENTITY_GRAVITY = new SimpleHolder<>(new RangedAttribute("forge.entity_gravity", 0.08D, -10.0D, 10.0D));
    public static final SimpleHolder<FluidType> WATER_TYPE = new SimpleHolder<>(NeoForgeMod.WATER_TYPE.value());
    public static final SimpleHolder<FluidType> LAVA_TYPE = new SimpleHolder<>(NeoForgeMod.LAVA_TYPE.value());

    private ForgeMod() {
    }

    public record SimpleHolder<T>(T value) {
        public T get() {
            return value;
        }
    }
}

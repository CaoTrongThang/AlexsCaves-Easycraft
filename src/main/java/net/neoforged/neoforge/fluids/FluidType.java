package net.neoforged.neoforge.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FluidType {

    private final Properties properties;

    public FluidType(Properties properties) {
        this.properties = properties;
    }

    public void initializeClient(Consumer<net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions> consumer) {
    }

    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        return false;
    }

    public boolean isVaporizedOnPlacement(Level level, BlockPos pos, FluidStack stack) {
        return false;
    }

    public void onVaporize(Player player, Level level, BlockPos pos, FluidStack stack) {
    }

    public SoundEvent getSound(Player player, Level level, BlockPos pos, Object action) {
        return properties.sounds.get(action);
    }

    public Properties getProperties() {
        return properties;
    }

    public static class Properties {
        private final Map<Object, SoundEvent> sounds = new HashMap<>();

        public static Properties create() {
            return new Properties();
        }

        public Properties lightLevel(int lightLevel) {
            return this;
        }

        public Properties density(int density) {
            return this;
        }

        public Properties viscosity(int viscosity) {
            return this;
        }

        public Properties pathType(PathType pathType) {
            return this;
        }

        public Properties adjacentPathType(PathType pathType) {
            return this;
        }

        public Properties sound(Object action, SoundEvent soundEvent) {
            sounds.put(action, soundEvent);
            return this;
        }
    }
}

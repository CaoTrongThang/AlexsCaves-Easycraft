package net.neoforged.neoforge.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class FluidInteractionRegistry {
    private static final Map<FluidType, List<InteractionInformation>> INTERACTIONS = new IdentityHashMap<>();

    private FluidInteractionRegistry() {
    }

    public static void addInteraction(FluidType sourceType, InteractionInformation interactionInformation) {
        INTERACTIONS.computeIfAbsent(sourceType, ignored -> new ArrayList<>()).add(interactionInformation);
    }

    public static boolean tryApplyInteraction(LevelAccessor level, BlockPos pos, FluidState sourceState) {
        FluidType sourceType = resolveFluidType(sourceState);

        if (sourceType == null) {
            return false;
        }

        List<InteractionInformation> interactions = INTERACTIONS.get(sourceType);

        if (interactions == null || interactions.isEmpty()) {
            return false;
        }

        for (Direction direction : Direction.values()) {
            FluidState neighborState = level.getFluidState(pos.relative(direction));

            if (neighborState.isEmpty()) {
                continue;
            }

            FluidType neighborType = resolveFluidType(neighborState);

            if (neighborType == null) {
                continue;
            }

            for (InteractionInformation interaction : interactions) {
                if (interaction.otherType() == neighborType) {
                    level.setBlock(pos, interaction.resultState().apply(neighborState), 3);
                    return true;
                }
            }
        }

        return false;
    }

    private static FluidType resolveFluidType(FluidState state) {
        if (state.getType() instanceof BaseFlowingFluid flowingFluid) {
            return flowingFluid.getFluidType();
        }

        if (state.is(FluidTags.WATER)) {
            return NeoForgeMod.WATER_TYPE.value();
        }

        if (state.is(FluidTags.LAVA)) {
            return NeoForgeMod.LAVA_TYPE.value();
        }

        return null;
    }

    public record InteractionInformation(FluidType otherType, Function<net.minecraft.world.level.material.FluidState, BlockState> resultState) {
    }
}

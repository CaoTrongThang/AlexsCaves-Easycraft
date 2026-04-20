package com.github.alexmodguy.alexscaves.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(PoiTypes.class)
public interface PoiTypesAccessor {
    @Invoker("registerBlockStates")
    static void registerBlockStates(Holder<PoiType> holder, Set<BlockState> blockStates) {
        throw new UnsupportedOperationException();
    }
}

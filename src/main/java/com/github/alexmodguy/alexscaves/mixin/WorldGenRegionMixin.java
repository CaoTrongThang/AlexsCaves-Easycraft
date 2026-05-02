package com.github.alexmodguy.alexscaves.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldGenRegion.class)
public abstract class WorldGenRegionMixin {

    @Shadow
    public abstract boolean hasChunk(int chunkX, int chunkZ);

    @Inject(method = "getBlockState", at = @At("HEAD"), cancellable = true)
    private void ac_getBlockState(BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (!this.hasChunk(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()))) {
            cir.setReturnValue(Blocks.VOID_AIR.defaultBlockState());
        }
    }

    @Inject(method = "getFluidState", at = @At("HEAD"), cancellable = true)
    private void ac_getFluidState(BlockPos pos, CallbackInfoReturnable<FluidState> cir) {
        if (!this.hasChunk(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()))) {
            cir.setReturnValue(Fluids.EMPTY.defaultFluidState());
        }
    }
}

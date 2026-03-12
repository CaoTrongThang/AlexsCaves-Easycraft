package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.misc.ACFluidHelper;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFluidMixin {

    @Shadow
    protected abstract boolean isAffectedByFluids();

    @Shadow
    protected abstract void jumpInLiquid(TagKey<Fluid> fluidTag);

    @Shadow
    public abstract boolean canStandOnFluid(FluidState fluidState);

    @Shadow
    public abstract void jumpFromGround();

    @Shadow
    protected int noJumpDelay;

    @Shadow
    protected boolean jumping;

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void ac_travelInCustomFluid(Vec3 movementVector, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (!livingEntity.isControlledByLocalInstance()) {
            return;
        }

        FluidState fluidState = livingEntity.level().getFluidState(livingEntity.blockPosition());
        if (!this.isAffectedByFluids() || this.canStandOnFluid(fluidState) || fluidState.is(FluidTags.WATER) || fluidState.is(FluidTags.LAVA)) {
            return;
        }

        if (fluidState.getType() instanceof BaseFlowingFluid flowingFluid && flowingFluid.getFluidType().move(fluidState, livingEntity, movementVector, livingEntity.getGravity())) {
            livingEntity.calculateEntityAnimation(livingEntity instanceof FlyingAnimal);
            ci.cancel();
        }
    }

    @Inject(
        method = "aiStep",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/LivingEntity;jumping:Z",
            ordinal = 1,
            shift = At.Shift.BEFORE
        )
    )
    private void ac_jumpInCustomFluid(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (!this.jumping || !this.isAffectedByFluids() || livingEntity.isInWater() || livingEntity.isInLava()) {
            return;
        }

        TagKey<Fluid> fluidTag = null;
        double fluidHeight = 0.0D;
        if (ACFluidHelper.isInAcid(livingEntity)) {
            fluidTag = ACFluidHelper.ACID;
            fluidHeight = ACFluidHelper.getAcidHeight(livingEntity);
        } else if (ACFluidHelper.isInPurpleSoda(livingEntity)) {
            fluidTag = ACFluidHelper.PURPLE_SODA;
            fluidHeight = ACFluidHelper.getPurpleSodaHeight(livingEntity);
        }

        if (fluidTag == null || fluidHeight <= 0.0D) {
            return;
        }

        double jumpThreshold = livingEntity.getFluidJumpThreshold();
        if (fluidHeight > jumpThreshold) {
            this.jumpInLiquid(fluidTag);
            return;
        }

        if (!livingEntity.onGround() && this.noJumpDelay == 0) {
            this.jumpFromGround();
            this.noJumpDelay = 10;
        }
    }
}

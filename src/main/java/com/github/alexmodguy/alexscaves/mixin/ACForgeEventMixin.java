package com.github.alexmodguy.alexscaves.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ACForgeEventMixin {

    @Inject(method = "die", at = @At("HEAD"))
    private void ac_livingDie(DamageSource source, CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new LivingDeathEvent((LivingEntity) (Object) this, source));
    }

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    private void ac_livingHeal(float healAmount, CallbackInfo ci) {
        LivingHealEvent event = new LivingHealEvent((LivingEntity) (Object) this);
        NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void ac_livingHurtPre(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingDamageEvent.Pre event = new LivingDamageEvent.Pre((LivingEntity) (Object) this, source, amount);
        NeoForge.EVENT_BUS.post(event);
        if (event.getNewDamage() <= 0.0F && amount > 0.0F) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)V"), cancellable = true)
    private void ac_livingIncomingDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingIncomingDamageEvent event = new LivingIncomingDamageEvent((LivingEntity) (Object) this, source);
        NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void ac_EntityTick(CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new EntityTickEvent.Post((LivingEntity) (Object) this));
    }

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("RETURN"))
    private void ac_livingAddEffect(MobEffectInstance effectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            NeoForge.EVENT_BUS.post(new MobEffectEvent.Added((LivingEntity) (Object) this, effectInstance));
        }
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "removeEffect(Lnet/minecraft/core/Holder;)Z", at = @At("RETURN"))
    private void ac_livingRemoveEffect(Holder<?> effect, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            NeoForge.EVENT_BUS.post(new MobEffectEvent.Remove((LivingEntity) (Object) this, (Holder<MobEffect>) effect));
        }
    }

    @Inject(method = "onEffectRemoved", at = @At("HEAD"))
    private void ac_livingExpireEffect(MobEffectInstance effectInstance, CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new MobEffectEvent.Expired((LivingEntity) (Object) this, effectInstance));
    }
}

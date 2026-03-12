package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.entity.util.EntityDropChanceAccessor;
import com.github.alexmodguy.alexscaves.server.entity.util.MobTargetAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements EntityDropChanceAccessor, MobTargetAccessor {

    @Shadow protected abstract float getEquipmentDropChance(EquipmentSlot p_21520_);

    @Shadow public abstract void setDropChance(EquipmentSlot p_21410_, float p_21411_);

    @Shadow private boolean canPickUpLoot;

    @Shadow protected abstract void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean playerKill);

    @Shadow protected GoalSelector goalSelector;

    @Shadow protected GoalSelector targetSelector;

    public MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public float ac_getEquipmentDropChance(EquipmentSlot equipmentSlot){
        return this.getEquipmentDropChance(equipmentSlot);
    }

    public void ac_setDropChance(EquipmentSlot equipmentSlot, float chance){
        this.setDropChance(equipmentSlot, chance);
    }

    public void ac_dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean playerKill){
        this.dropCustomDeathLoot(serverLevel, damageSource, playerKill);
    }

    @Override
    public GoalSelector ac_getGoalSelector() {
        return this.goalSelector;
    }

    @Override
    public GoalSelector ac_getTargetSelector() {
        return this.targetSelector;
    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void ac_changeTarget(@Nullable LivingEntity target, CallbackInfo ci) {
        LivingChangeTargetEvent event = new LivingChangeTargetEvent((Mob) (Object) this, target);
        NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "finalizeSpawn", at = @At("TAIL"))
    private void ac_finalizeSpawn(net.minecraft.world.level.ServerLevelAccessor level, net.minecraft.world.DifficultyInstance difficulty, net.minecraft.world.entity.MobSpawnType reason, net.minecraft.world.entity.SpawnGroupData spawnData, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<net.minecraft.world.entity.SpawnGroupData> cir) {
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent((Mob) (Object) this));
    }
}

package net.neoforged.neoforge.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;

import java.util.Collection;

public final class CommonHooks {
    private CommonHooks() {
    }

    public static float[] onLivingFall(LivingEntity entity, float distance, float damageMultiplier) {
        return new float[]{distance, damageMultiplier};
    }

    public static boolean onLivingDrops(LivingEntity entity, DamageSource damageSource, Collection<ItemEntity> drops, boolean recentlyHit) {
        return false;
    }

    public static LivingKnockBackEvent onLivingKnockBack(LivingEntity entity, float strength, double ratioX, double ratioZ) {
        return new LivingKnockBackEvent(entity, strength, ratioX, ratioZ);
    }
}

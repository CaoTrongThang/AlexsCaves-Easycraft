package com.github.alexmodguy.alexscaves.forge_shim.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import com.github.alexmodguy.alexscaves.forge_shim.event.entity.living.LivingKnockBackEvent;

import java.util.Collection;

public final class ForgeHooks {
    private ForgeHooks() {
    }

    public static float[] onLivingFall(LivingEntity entity, float distance, float damageMultiplier) {
        return new float[]{distance, damageMultiplier};
    }

    public static int getLootingLevel(Entity entity, Entity target, DamageSource source) {
        return 0;
    }

    public static boolean onLivingDrops(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops, int lootingLevel, boolean recentlyHit) {
        return false;
    }

    public static LivingKnockBackEvent onLivingKnockBack(LivingEntity entity, float strength, double ratioX, double ratioZ) {
        return new LivingKnockBackEvent(entity, strength, ratioX, ratioZ);
    }
}

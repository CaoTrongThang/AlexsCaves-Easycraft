package com.github.alexmodguy.alexscaves.forge_shim.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Cancelable;

@Cancelable
public class LivingAttackEvent extends LivingEvent {
    private final DamageSource source;

    public LivingAttackEvent(LivingEntity entity, DamageSource source) {
        super(entity);
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }
}

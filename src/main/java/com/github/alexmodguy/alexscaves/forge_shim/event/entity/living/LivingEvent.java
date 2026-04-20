package com.github.alexmodguy.alexscaves.forge_shim.event.entity.living;

import net.minecraft.world.entity.LivingEntity;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class LivingEvent extends Event {
    private final LivingEntity entity;

    public LivingEvent(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public static class LivingTickEvent extends LivingEvent {
        public LivingTickEvent(LivingEntity entity) {
            super(entity);
        }
    }
}

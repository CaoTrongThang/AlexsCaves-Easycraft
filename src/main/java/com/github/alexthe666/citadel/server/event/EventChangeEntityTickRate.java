package com.github.alexthe666.citadel.server.event;

import net.minecraft.world.entity.Entity;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Cancelable;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

@Cancelable
public class EventChangeEntityTickRate extends Event {
    private final Entity entity;
    private final float targetTickRate;

    public EventChangeEntityTickRate(Entity entity, float targetTickRate) {
        this.entity = entity;
        this.targetTickRate = targetTickRate;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getTargetTickRate() {
        return targetTickRate;
    }
}

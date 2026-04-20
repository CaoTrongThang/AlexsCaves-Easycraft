package com.github.alexmodguy.alexscaves.forge_shim.event.entity;

import net.minecraft.world.entity.Entity;

public class EntityTravelToDimensionEvent extends EntityEvent {
    public EntityTravelToDimensionEvent(Entity entity) {
        super(entity);
    }
}

package net.neoforged.neoforge.event.entity;

import net.minecraft.world.entity.Entity;

public class EntityTravelToDimensionEvent extends EntityEvent {
    public EntityTravelToDimensionEvent(Entity entity) {
        super(entity);
    }
}

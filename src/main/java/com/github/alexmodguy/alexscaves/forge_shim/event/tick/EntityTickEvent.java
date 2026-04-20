package com.github.alexmodguy.alexscaves.forge_shim.event.tick;

import net.minecraft.world.entity.Entity;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class EntityTickEvent extends Event {
    private final Entity entity;

    public EntityTickEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public static class Post extends EntityTickEvent {
        public Post(Entity entity) {
            super(entity);
        }
    }
}

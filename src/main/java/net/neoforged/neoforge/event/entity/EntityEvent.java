package net.neoforged.neoforge.event.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.neoforged.bus.api.Event;

public class EntityEvent extends Event {
    private final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public static class Size extends EntityEvent {
        private final EntityDimensions oldSize;
        private EntityDimensions newSize;

        public Size(Entity entity, EntityDimensions oldSize, EntityDimensions newSize) {
            super(entity);
            this.oldSize = oldSize;
            this.newSize = newSize;
        }

        public EntityDimensions getOldSize() {
            return oldSize;
        }

        public EntityDimensions getNewSize() {
            return newSize;
        }

        public void setNewSize(EntityDimensions newSize) {
            this.newSize = newSize;
        }
    }
}

package net.neoforged.neoforge.event.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.bus.api.Event;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityAttributeCreationEvent extends Event {
    private final Map<EntityType<? extends LivingEntity>, AttributeSupplier> attributes = new LinkedHashMap<>();

    public <T extends LivingEntity> void put(EntityType<T> entityType, AttributeSupplier attributes) {
        this.attributes.put(entityType, attributes);
    }

    public Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAttributes() {
        return attributes;
    }
}

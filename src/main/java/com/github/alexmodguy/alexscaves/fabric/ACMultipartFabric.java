package com.github.alexmodguy.alexscaves.fabric;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.MultiPartEntity;
import net.neoforged.neoforge.entity.PartEntity;

public final class ACMultipartFabric {

    private ACMultipartFabric() {
    }

    public static void registerCommon() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> addParts(world, entity));
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> removeParts(world, entity));
    }

    static void addParts(Level level, Entity entity) {
        if (!(entity instanceof MultiPartEntity multipartEntity) || !multipartEntity.isMultipartEntity()) {
            return;
        }
        PartEntity<?>[] parts = multipartEntity.getParts();
        if (parts == null) {
            return;
        }
        var partEntityMap = ((MultipartEntityLevel) level).alexscaves$getPartEntityMap();
        for (PartEntity<?> part : parts) {
            partEntityMap.put(part.getId(), part);
        }
    }

    static void removeParts(Level level, Entity entity) {
        if (!(entity instanceof MultiPartEntity multipartEntity) || !multipartEntity.isMultipartEntity()) {
            return;
        }
        PartEntity<?>[] parts = multipartEntity.getParts();
        if (parts == null) {
            return;
        }
        var partEntityMap = ((MultipartEntityLevel) level).alexscaves$getPartEntityMap();
        for (PartEntity<?> part : parts) {
            partEntityMap.remove(part.getId());
        }
    }
}

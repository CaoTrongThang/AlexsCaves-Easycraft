package com.github.alexmodguy.alexscaves.forge_shim.event.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

import java.lang.reflect.Method;

public class RegisterSpawnPlacementsEvent extends Event {
    public enum Operation {
        AND,
        OR,
        REPLACE
    }

    public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacements.Type placementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate, Operation operation) {
        try {
            Method method = SpawnPlacements.class.getDeclaredMethod("register", EntityType.class, SpawnPlacements.Type.class, Heightmap.Types.class, SpawnPlacements.SpawnPredicate.class);
            method.setAccessible(true);
            method.invoke(null, entityType, placementType, heightmapType, predicate);
        } catch (ReflectiveOperationException ignored) {
        }
    }
}

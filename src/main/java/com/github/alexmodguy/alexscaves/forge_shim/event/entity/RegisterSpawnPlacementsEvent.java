package com.github.alexmodguy.alexscaves.forge_shim.event.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class RegisterSpawnPlacementsEvent extends Event {
    public enum Operation {
        AND,
        OR,
        REPLACE
    }

    public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacements.Type placementType,
            Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate, Operation operation) {
        SpawnPlacements.register(entityType, placementType, heightmapType, predicate);
    }
}

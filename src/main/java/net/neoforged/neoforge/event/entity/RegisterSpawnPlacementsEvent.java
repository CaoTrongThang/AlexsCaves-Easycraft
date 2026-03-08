package net.neoforged.neoforge.event.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.Event;

public class RegisterSpawnPlacementsEvent extends Event {
    public enum Operation {
        AND,
        OR,
        REPLACE
    }

    public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType placementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate, Operation operation) {
        // Fabric/vanilla only exposes direct registration; the first registration wins here.
        SpawnPlacements.register(entityType, placementType, heightmapType, predicate);
    }
}

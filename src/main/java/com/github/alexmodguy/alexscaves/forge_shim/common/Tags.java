package com.github.alexmodguy.alexscaves.forge_shim.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class Tags {
    private Tags() {
    }

    public static final class Biomes {
        public static final TagKey<Biome> IS_SNOWY = create("is_snowy");
        public static final TagKey<Biome> IS_WATER = create("is_water");
        public static final TagKey<Biome> IS_DESERT = create("is_desert");
        public static final TagKey<Biome> IS_MOUNTAIN = create("is_mountain");
        public static final TagKey<Biome> IS_CONIFEROUS = create("is_coniferous");
        public static final TagKey<Biome> IS_SWAMP = create("is_swamp");
        public static final TagKey<Biome> IS_RARE = create("is_rare");
        public static final TagKey<Biome> IS_MUSHROOM = create("is_mushroom");
        public static final TagKey<Biome> IS_SPOOKY = create("is_spooky");
        public static final TagKey<Biome> IS_PLAINS = create("is_plains");

        private Biomes() {
        }

        private static TagKey<Biome> create(String path) {
            return TagKey.create(Registries.BIOME, new ResourceLocation("forge", path));
        }
    }
}

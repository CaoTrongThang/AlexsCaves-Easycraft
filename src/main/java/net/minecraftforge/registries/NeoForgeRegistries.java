package net.minecraftforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class NeoForgeRegistries {
    private NeoForgeRegistries() {
    }

    public static final class Keys {
        public static final ResourceKey<Registry<Object>> ATTACHMENT_TYPES = create("attachment_type");
        public static final ResourceKey<Registry<Object>> ENTITY_DATA_SERIALIZERS = create("entity_data_serializer");
        public static final ResourceKey<Registry<Object>> FLUID_TYPES = create("fluid_type");
        public static final ResourceKey<Registry<Object>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = create("global_loot_modifier_serializer");

        private Keys() {
        }

        @SuppressWarnings("unchecked")
        private static <T> ResourceKey<Registry<T>> create(String path) {
            return (ResourceKey<Registry<T>>) (ResourceKey<?>) ResourceKey.createRegistryKey(new ResourceLocation("neoforge", path));
        }
    }
}

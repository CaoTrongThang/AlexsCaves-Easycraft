package net.minecraftforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public final class ForgeRegistries {
    public static final RegistryView<net.minecraft.world.level.block.Block> BLOCKS = new RegistryView<>(BuiltInRegistries.BLOCK);
    public static final RegistryView<net.minecraft.world.level.block.entity.BlockEntityType<?>> BLOCK_ENTITY_TYPES = new RegistryView<>(BuiltInRegistries.BLOCK_ENTITY_TYPE);
    public static final RegistryView<net.minecraft.world.item.Item> ITEMS = new RegistryView<>(BuiltInRegistries.ITEM);
    public static final RegistryView<net.minecraft.world.entity.EntityType<?>> ENTITY_TYPES = new RegistryView<>(BuiltInRegistries.ENTITY_TYPE);
    public static final RegistryView<net.minecraft.sounds.SoundEvent> SOUND_EVENTS = new RegistryView<>(BuiltInRegistries.SOUND_EVENT);
    public static final RegistryView<net.minecraft.world.effect.MobEffect> MOB_EFFECTS = new RegistryView<>(BuiltInRegistries.MOB_EFFECT);
    public static final RegistryView<net.minecraft.world.item.alchemy.Potion> POTIONS = new RegistryView<>(BuiltInRegistries.POTION);
    public static final RegistryView<net.minecraft.core.particles.ParticleType<?>> PARTICLE_TYPES = new RegistryView<>(BuiltInRegistries.PARTICLE_TYPE);
    public static final RegistryView<net.minecraft.world.entity.ai.village.poi.PoiType> POI_TYPES = new RegistryView<>(BuiltInRegistries.POINT_OF_INTEREST_TYPE);
    public static final RegistryView<net.minecraft.world.inventory.MenuType<?>> MENU_TYPES = new RegistryView<>(BuiltInRegistries.MENU);
    public static final RegistryView<net.minecraft.world.level.levelgen.carver.WorldCarver<?>> WORLD_CARVERS = new RegistryView<>(BuiltInRegistries.CARVER);
    public static final RegistryView<net.minecraft.world.level.levelgen.feature.Feature<?>> FEATURES = new RegistryView<>(BuiltInRegistries.FEATURE);
    public static final RegistryView<net.minecraft.world.item.enchantment.Enchantment> ENCHANTMENTS = new RegistryView<>(BuiltInRegistries.ENCHANTMENT);
    public static final RegistryView<net.minecraft.world.level.material.Fluid> FLUIDS = new RegistryView<>(BuiltInRegistries.FLUID);
    @SuppressWarnings("unchecked")
    public static final RegistryView<net.minecraft.world.level.biome.Biome> BIOMES = new RegistryView<>((Registry<net.minecraft.world.level.biome.Biome>) (Registry<?>) BuiltInRegistries.REGISTRY.get(Registries.BIOME.location()));

    private ForgeRegistries() {
    }

    public static final class Keys {
        public static final ResourceKey<Registry<Object>> ENTITY_DATA_SERIALIZERS = create("entity_data_serializer");
        public static final ResourceKey<Registry<Object>> FLUID_TYPES = create("fluid_type");
        public static final ResourceKey<Registry<Object>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = create("global_loot_modifier_serializer");
        public static final ResourceKey<Registry<Object>> BIOMES = cast(Registries.BIOME);

        private Keys() {
        }

        @SuppressWarnings("unchecked")
        private static <T> ResourceKey<Registry<T>> cast(ResourceKey<? extends Registry<?>> key) {
            return (ResourceKey<Registry<T>>) (ResourceKey<?>) key;
        }

        @SuppressWarnings("unchecked")
        private static <T> ResourceKey<Registry<T>> create(String path) {
            return (ResourceKey<Registry<T>>) (ResourceKey<?>) ResourceKey.createRegistryKey(new ResourceLocation("forge", path));
        }
    }

    public static final class RegistryView<T> {
        private final Registry<T> registry;

        RegistryView(Registry<T> registry) {
            this.registry = registry;
        }

        public ResourceKey<? extends Registry<T>> key() {
            return registry.key();
        }

        public T getValue(ResourceLocation id) {
            return registry.get(id);
        }

        public ResourceLocation getKey(T value) {
            return registry.getKey(value);
        }

        public Collection<T> getValues() {
            return registry.stream().toList();
        }

        public Registry<T> unwrap() {
            return registry;
        }
    }
}

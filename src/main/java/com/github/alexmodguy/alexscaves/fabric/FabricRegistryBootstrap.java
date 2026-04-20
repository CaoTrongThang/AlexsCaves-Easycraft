package com.github.alexmodguy.alexscaves.fabric;

import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.client.model.layered.ACModelLayers;
import com.github.alexmodguy.alexscaves.client.particle.ACParticleRegistry;
import com.github.alexmodguy.alexscaves.client.render.entity.layer.ClientLayerRegistry;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.github.alexmodguy.alexscaves.server.block.poi.ACPOIRegistry;
import com.github.alexmodguy.alexscaves.server.enchantment.ACEnchantmentRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityDataRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACFrogRegistry;
import com.github.alexmodguy.alexscaves.server.inventory.ACMenuRegistry;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.level.carver.ACCarverRegistry;
import com.github.alexmodguy.alexscaves.server.level.feature.ACFeatureRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.ACStructureRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.piece.ACStructurePieceRegistry;
import com.github.alexmodguy.alexscaves.server.level.structure.processor.ACStructureProcessorRegistry;
import com.github.alexmodguy.alexscaves.server.level.surface.ACSurfaceRuleConditionRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACCreativeTabRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACLootTableRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACPotPatternRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.recipe.ACRecipeRegistry;
import com.github.alexmodguy.alexscaves.forge_shim.registries.ForgeRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import com.github.alexthe666.citadel.CitadelItemRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import com.github.alexmodguy.alexscaves.forge_shim.event.entity.EntityAttributeCreationEvent;
import com.github.alexmodguy.alexscaves.forge_shim.event.entity.SpawnPlacementRegisterEvent;
import com.github.alexmodguy.alexscaves.forge_shim.registries.DeferredRegister;

final class FabricRegistryBootstrap {

    private FabricRegistryBootstrap() {
    }

    static void bootstrapCommon() {
        register(
                CitadelItemRegistry.DEF_REG,
                ACBlockRegistry.DEF_REG,
                ACFluidRegistry.FLUID_DEF_REG,
                ACEntityRegistry.DEF_REG,
                ACParticleRegistry.DEF_REG,
                ACBlockEntityRegistry.DEF_REG,
                ACMenuRegistry.DEF_REG,
                ACSoundRegistry.DEF_REG,
                ACEffectRegistry.DEF_REG,
                ACEffectRegistry.POTION_DEF_REG,
                ACItemRegistry.DEF_REG,
                ACEnchantmentRegistry.DEF_REG,
                ACCreativeTabRegistry.DEF_REG,
                ACRecipeRegistry.TYPE_DEF_REG,
                ACRecipeRegistry.DEF_REG,
                ACPOIRegistry.DEF_REG,
                ACFrogRegistry.DEF_REG,
                ACLootTableRegistry.LOOT_FUNCTION_DEF_REG,
                ACFeatureRegistry.DEF_REG,
                ACCarverRegistry.DEF_REG,
                ACStructureRegistry.DEF_REG,
                ACStructurePieceRegistry.DEF_REG,
                ACStructureProcessorRegistry.DEF_REG,
                ACSurfaceRuleConditionRegistry.DEF_REG,
                ACEntityDataRegistry.DEF_REG,
                ACFluidRegistry.FLUID_TYPE_DEF_REG,
                ACLootTableRegistry.GLOBAL_LOOT_MODIFIER_DEF_REG,
                ACPotPatternRegistry.DEF_REG);
        ACFluidRegistry.postInit();
        bootstrapEntities();
    }

    static void bootstrapClient() {
        ACModelLayers.registerFabric();
        ClientLayerRegistry.registerFabric();
        ClientProxy.registerFabricShaders();
        ClientProxy.registerFabricBuiltinItemRenderers();
        ClientProxy.registerFabricFluidRendering();
    }

    @SafeVarargs
    private static void register(DeferredRegister<?>... registers) {
        for (DeferredRegister<?> register : registers) {
            register.register(null);
            if (register.getRegistryKey().equals(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS)) {
                register.getEntries().forEach(ro -> {
                    EntityDataSerializers.registerSerializer((EntityDataSerializer<?>) ro.get());
                });
            } else if (register.getRegistryKey()
                    .equals(net.minecraft.core.registries.Registries.POINT_OF_INTEREST_TYPE)) {
                register.getEntries().forEach(ro -> {
                    net.minecraft.world.entity.ai.village.poi.PoiType poiType = (net.minecraft.world.entity.ai.village.poi.PoiType) ro
                            .get();
                    @SuppressWarnings("unchecked")
                    net.minecraft.resources.ResourceKey<net.minecraft.world.entity.ai.village.poi.PoiType> key = (net.minecraft.resources.ResourceKey<net.minecraft.world.entity.ai.village.poi.PoiType>) (Object) ro
                            .getId();
                    net.minecraft.core.Holder<net.minecraft.world.entity.ai.village.poi.PoiType> holder = net.minecraft.core.registries.BuiltInRegistries.POINT_OF_INTEREST_TYPE
                            .getHolder(key).orElse(null);
                    if (holder != null) {
                        try {
                            com.github.alexmodguy.alexscaves.mixin.PoiTypesAccessor.registerBlockStates(holder,
                                    poiType.matchingStates());
                        } catch (Exception e) {
                        }
                    }
                });
            }
        }
    }

    private static void bootstrapEntities() {
        EntityAttributeCreationEvent attributeEvent = new EntityAttributeCreationEvent();
        ACEntityRegistry.initializeAttributes(attributeEvent);
        for (var entry : attributeEvent.getAttributes().entrySet()) {
            FabricDefaultAttributeRegistry.register(entry.getKey(), entry.getValue());
        }
        ACEntityRegistry.spawnPlacements(new SpawnPlacementRegisterEvent());
    }
}

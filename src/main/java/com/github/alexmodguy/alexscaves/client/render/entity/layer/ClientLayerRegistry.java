package com.github.alexmodguy.alexscaves.client.render.entity.layer;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.render.entity.LivingEntityRendererAccessor;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import com.github.alexmodguy.alexscaves.forge_shim.api.distmarker.Dist;
import com.github.alexmodguy.alexscaves.forge_shim.api.distmarker.OnlyIn;
import com.github.alexmodguy.alexscaves.forge_shim.client.event.EntityRenderersEvent;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.SubscribeEvent;
import com.github.alexmodguy.alexscaves.forge_shim.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class ClientLayerRegistry {

    public static void registerFabric() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityType != EntityType.ENDER_DRAGON) {
                registerFabricLayer(entityRenderer, registrationHelper);
            }
        });
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(
                ForgeRegistries.ENTITY_TYPES.getValues().stream()
                        .filter(DefaultAttributes::hasSupplier)
                        .map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                        .collect(Collectors.toList()));
        entityTypes.forEach((entityType -> {
            addLayerIfApplicable(entityType, event);
        }));
        for (String skinType : event.getSkins()) {
            event.getSkin(skinType).addLayer(new ACPotionEffectLayer(event.getSkin(skinType)));
        }
    }

    private static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, EntityRenderersEvent.AddLayers event) {
        LivingEntityRenderer renderer = null;
        if (entityType != EntityType.ENDER_DRAGON) {
            try {
                renderer = event.getRenderer(entityType);
            } catch (Exception e) {
                AlexsCaves.LOGGER.warn("Could not apply radiation glow layer to " + ForgeRegistries.ENTITY_TYPES.getKey(entityType) + ", has custom renderer that is not LivingEntityRenderer.");
            }
            if (renderer != null) {
                ((LivingEntityRendererAccessor) renderer).addACLayer(new ACPotionEffectLayer(renderer));
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void registerFabricLayer(LivingEntityRenderer<?, ?> livingRenderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper) {
        registrationHelper.register(new ACPotionEffectLayer(livingRenderer));
    }
}

package com.github.alexmodguy.alexscaves.client.render.entity.layer;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.render.entity.LivingEntityRendererAccessor;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class ClientLayerRegistry {

    public static void registerFabric() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
                (entityType, entityRenderer, registrationHelper, context) -> {
                    if (entityType != EntityType.ENDER_DRAGON
                            && entityRenderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
                        registerFabricLayer(livingRenderer, registrationHelper);
                    }
                });
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(
                BuiltInRegistries.ENTITY_TYPE.stream()
                        .filter(DefaultAttributes::hasSupplier)
                        .map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                        .collect(Collectors.toList()));
        entityTypes.forEach((entityType -> {
            addLayerIfApplicable(entityType, event);
        }));
        for (PlayerSkin.Model modelType : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(modelType);
            if (renderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
                ((LivingEntityRendererAccessor) livingRenderer).addACLayer(new ACPotionEffectLayer(livingRenderer));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, EntityRenderersEvent.AddLayers event) {
        LivingEntityRenderer renderer = null;
        if (entityType != EntityType.ENDER_DRAGON) {
            try {
                renderer = (LivingEntityRenderer) event.getRenderer(entityType);
            } catch (Exception e) {
                AlexsCaves.LOGGER.warn("Could not apply radiation glow layer to " + BuiltInRegistries.ENTITY_TYPE.getKey(entityType) + ", has custom renderer that is not LivingEntityRenderer.");
            }
            if (renderer != null) {
                ((LivingEntityRendererAccessor) renderer).addACLayer(new ACPotionEffectLayer(renderer));
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void registerFabricLayer(LivingEntityRenderer<?, ?> livingRenderer,
            LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper) {
        registrationHelper.register(new ACPotionEffectLayer(livingRenderer));
    }
}

package net.minecraftforge.client.event;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Supplier;

public final class EntityRenderersEvent {
    private EntityRenderersEvent() {
    }

    public static class RegisterLayerDefinitions {
        public void registerLayerDefinition(ModelLayerLocation location, Supplier<LayerDefinition> supplier) {
        }
    }

    public static class AddLayers {
        public List<String> getSkins() {
            return List.of();
        }

        public LivingEntityRenderer<? extends Player, ?> getSkin(String model) {
            return null;
        }

        public LivingEntityRenderer<? extends Entity, ?> getRenderer(EntityType<?> entityType) {
            return null;
        }
    }
}

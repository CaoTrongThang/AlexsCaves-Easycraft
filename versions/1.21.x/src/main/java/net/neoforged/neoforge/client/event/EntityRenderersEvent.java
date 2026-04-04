package net.neoforged.neoforge.client.event;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;

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
        public List<PlayerSkin.Model> getSkins() {
            return List.of();
        }

        public EntityRenderer getSkin(PlayerSkin.Model model) {
            return null;
        }

        public EntityRenderer getRenderer(EntityType<?> entityType) {
            return null;
        }
    }
}

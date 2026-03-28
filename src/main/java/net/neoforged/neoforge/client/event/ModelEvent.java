package net.neoforged.neoforge.client.event;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class ModelEvent {
    private ModelEvent() {
    }

    public static class ModifyBakingResult {
        private final Map<ModelResourceLocation, BakedModel> models = new HashMap<>();

        public Map<ModelResourceLocation, BakedModel> getModels() {
            return models;
        }
    }
}

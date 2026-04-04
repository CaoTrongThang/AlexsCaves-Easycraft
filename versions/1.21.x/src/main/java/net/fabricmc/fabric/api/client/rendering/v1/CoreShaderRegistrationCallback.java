package net.fabricmc.fabric.api.client.rendering.v1;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;

public interface CoreShaderRegistrationCallback {
    Event EVENT = callback -> {
    };

    void registerShaders(Context context);

    interface Context {
        void register(ResourceLocation id, VertexFormat vertexFormat);
    }

    interface Event {
        void register(CoreShaderRegistrationCallback callback);
    }
}

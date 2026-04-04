package com.github.alexmodguy.alexscaves.client.render.entity.layer;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.model.NucleeperModel;
import com.github.alexmodguy.alexscaves.client.render.entity.compat.EnergySwirlLayer121X;
import com.github.alexmodguy.alexscaves.client.render.entity.compat.RenderLayerParent121X;
import com.github.alexmodguy.alexscaves.server.entity.living.NucleeperEntity;
import net.minecraft.resources.ResourceLocation;

public class NucleeperEnergySwirlLayer extends EnergySwirlLayer121X<NucleeperEntity, NucleeperModel> {
    private static final ResourceLocation POWER_LOCATION = ResourceLocation.fromNamespaceAndPath(AlexsCaves.MODID,
            "textures/entity/nucleeper/nucleeper_charged.png");
    private final NucleeperModel model = new NucleeperModel(1.0F);

    public NucleeperEnergySwirlLayer(RenderLayerParent121X<NucleeperEntity, NucleeperModel> renderer) {
        super(renderer);
    }

    protected float xOffset(float f) {
        return f * 0.01F;
    }

    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    protected Object model() {
        return this.model;
    }
}

package net.minecraft.client.renderer.block.model;

import net.minecraft.resources.ResourceLocation;

public class ItemOverride {
    private String model = "minecraft:air";

    public ResourceLocation getModel() {
        return ResourceLocation.parse(this.model);
    }
}

package net.minecraftforge.client.gui.overlay;

import net.minecraft.resources.ResourceLocation;

public enum VanillaGuiOverlay {
    CROSSHAIR("crosshair"),
    EXPERIENCE_BAR("experience_bar"),
    JUMP_BAR("jump_bar"),
    ITEM_NAME("item_name"),
    PLAYER_HEALTH("player_health");

    private final ResourceLocation id;

    VanillaGuiOverlay(String path) {
        this.id = new ResourceLocation(path);
    }

    public ResourceLocation id() {
        return id;
    }
}

package com.github.alexmodguy.alexscaves.fabric;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public final class ClientAccess {
    private ClientAccess() {
    }

    public static void loadEffect(GameRenderer renderer, ResourceLocation resourceLocation) {
        renderer.loadEffect(resourceLocation);
    }

    public static boolean hasCurrentEffect(GameRenderer renderer, ResourceLocation resourceLocation) {
        return renderer.currentEffect() != null
                && resourceLocation.toString().equals(renderer.currentEffect().getName());
    }

    public static void moveCamera(Camera camera, double x, double y, double z) {
        camera.move(x, y, z);
    }

    public static double getMaxZoom(Camera camera, double zoom) {
        return camera.getMaxZoom(zoom);
    }

    public static Advancement getSelectedAdvancement(AdvancementsScreen screen) {
        if (screen.selectedTab != null) {
            return screen.selectedTab.getAdvancement();
        }
        return null;
    }

    public static Advancement getAdvancement(AdvancementWidget widget) {
        return widget.advancement;
    }

    public static AdvancementWidget getParent(AdvancementWidget widget) {
        return widget.parent;
    }

    public static AdvancementProgress getProgress(AdvancementWidget widget) {
        return widget.progress;
    }
}

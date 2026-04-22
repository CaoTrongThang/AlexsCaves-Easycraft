package com.github.alexmodguy.alexscaves.fabric;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ClientAccess {
    private static Field selectedTabField;
    private static Method tabGetAdvancementMethod;
    private static Method loadEffectMethod;
    private static Method cameraMoveMethod;
    private static Method cameraGetMaxZoomMethod;
    private static Field advancementWidgetAdvancementField;
    private static Field advancementWidgetParentField;
    private static Field advancementWidgetProgressField;

    private ClientAccess() {
    }

    public static void loadEffect(GameRenderer renderer, ResourceLocation resourceLocation) {
        try {
            if (loadEffectMethod == null) {
                loadEffectMethod = GameRenderer.class.getDeclaredMethod("loadEffect", ResourceLocation.class);
                loadEffectMethod.setAccessible(true);
            }
            loadEffectMethod.invoke(renderer, resourceLocation);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public static boolean hasCurrentEffect(GameRenderer renderer, ResourceLocation resourceLocation) {
        return renderer.currentEffect() != null
                && resourceLocation.toString().equals(renderer.currentEffect().getName());
    }

    public static void moveCamera(Camera camera, double x, double y, double z) {
        try {
            if (cameraMoveMethod == null) {
                cameraMoveMethod = Camera.class.getDeclaredMethod("move", double.class, double.class, double.class);
                cameraMoveMethod.setAccessible(true);
            }
            cameraMoveMethod.invoke(camera, x, y, z);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public static double getMaxZoom(Camera camera, double zoom) {
        try {
            if (cameraGetMaxZoomMethod == null) {
                cameraGetMaxZoomMethod = Camera.class.getDeclaredMethod("getMaxZoom", double.class);
                cameraGetMaxZoomMethod.setAccessible(true);
            }
            return ((Number) cameraGetMaxZoomMethod.invoke(camera, zoom)).doubleValue();
        } catch (ReflectiveOperationException ignored) {
            return zoom;
        }
    }

    public static Advancement getSelectedAdvancement(AdvancementsScreen screen) {
        try {
            if (selectedTabField == null) {
                selectedTabField = AdvancementsScreen.class.getDeclaredField("selectedTab");
                selectedTabField.setAccessible(true);
            }
            Object selectedTab = selectedTabField.get(screen);
            if (selectedTab == null) {
                return null;
            }
            if (tabGetAdvancementMethod == null) {
                tabGetAdvancementMethod = selectedTab.getClass().getDeclaredMethod("getAdvancement");
                tabGetAdvancementMethod.setAccessible(true);
            }
            Object advancement = tabGetAdvancementMethod.invoke(selectedTab);
            return advancement instanceof Advancement cast ? cast : null;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    public static Advancement getAdvancement(AdvancementWidget widget) {
        try {
            if (advancementWidgetAdvancementField == null) {
                advancementWidgetAdvancementField = AdvancementWidget.class.getDeclaredField("advancement");
                advancementWidgetAdvancementField.setAccessible(true);
            }
            Object advancement = advancementWidgetAdvancementField.get(widget);
            return advancement instanceof Advancement cast ? cast : null;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    public static AdvancementWidget getParent(AdvancementWidget widget) {
        try {
            if (advancementWidgetParentField == null) {
                advancementWidgetParentField = AdvancementWidget.class.getDeclaredField("parent");
                advancementWidgetParentField.setAccessible(true);
            }
            Object parent = advancementWidgetParentField.get(widget);
            return parent instanceof AdvancementWidget cast ? cast : null;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    public static AdvancementProgress getProgress(AdvancementWidget widget) {
        try {
            if (advancementWidgetProgressField == null) {
                advancementWidgetProgressField = AdvancementWidget.class.getDeclaredField("progress");
                advancementWidgetProgressField.setAccessible(true);
            }
            Object progress = advancementWidgetProgressField.get(widget);
            return progress instanceof AdvancementProgress cast ? cast : null;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}

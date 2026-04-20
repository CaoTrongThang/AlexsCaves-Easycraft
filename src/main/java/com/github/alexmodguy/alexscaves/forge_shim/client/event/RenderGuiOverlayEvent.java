package com.github.alexmodguy.alexscaves.forge_shim.client.event;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphics;
import com.github.alexmodguy.alexscaves.forge_shim.client.gui.overlay.VanillaGuiOverlay;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Cancelable;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class RenderGuiOverlayEvent extends Event {
    private final GuiGraphics guiGraphics;
    private final Window window;
    private final VanillaGuiOverlay overlay;

    protected RenderGuiOverlayEvent(GuiGraphics guiGraphics, Window window, VanillaGuiOverlay overlay) {
        this.guiGraphics = guiGraphics;
        this.window = window;
        this.overlay = overlay;
    }

    public GuiGraphics getGuiGraphics() {
        return guiGraphics;
    }

    public Window getWindow() {
        return window;
    }

    public VanillaGuiOverlay getOverlay() {
        return overlay;
    }

    @Cancelable
    public static class Pre extends RenderGuiOverlayEvent {
        public Pre(GuiGraphics guiGraphics, Window window, VanillaGuiOverlay overlay) {
            super(guiGraphics, window, overlay);
        }
    }

    public static class Post extends RenderGuiOverlayEvent {
        public Post(GuiGraphics guiGraphics, Window window, VanillaGuiOverlay overlay) {
            super(guiGraphics, window, overlay);
        }
    }
}

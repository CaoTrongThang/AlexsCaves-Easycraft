package net.minecraftforge.client.event;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

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

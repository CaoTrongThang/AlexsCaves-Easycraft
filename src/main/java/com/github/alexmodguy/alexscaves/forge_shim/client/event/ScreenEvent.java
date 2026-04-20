package com.github.alexmodguy.alexscaves.forge_shim.client.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class ScreenEvent extends Event {
    public static class BackgroundRendered extends Event {
        private final Screen screen;
        private final GuiGraphics guiGraphics;

        public BackgroundRendered(Screen screen, GuiGraphics guiGraphics) {
            this.screen = screen;
            this.guiGraphics = guiGraphics;
        }

        public Screen getScreen() {
            return screen;
        }

        public GuiGraphics getGuiGraphics() {
            return guiGraphics;
        }
    }
}

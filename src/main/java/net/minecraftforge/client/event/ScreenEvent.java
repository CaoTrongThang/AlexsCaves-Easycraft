package net.minecraftforge.client.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.eventbus.api.Event;

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

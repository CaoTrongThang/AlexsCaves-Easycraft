package net.minecraftforge.client.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.BossEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class CustomizeGuiOverlayEvent extends Event {
    @Cancelable
    public static class BossEventProgress extends Event {
        private final GuiGraphics guiGraphics;
        private final BossEvent bossEvent;
        private final int x;
        private final int y;
        private int increment;

        public BossEventProgress(GuiGraphics guiGraphics, BossEvent bossEvent, int x, int y, int increment) {
            this.guiGraphics = guiGraphics;
            this.bossEvent = bossEvent;
            this.x = x;
            this.y = y;
            this.increment = increment;
        }

        public GuiGraphics getGuiGraphics() {
            return guiGraphics;
        }

        public BossEvent getBossEvent() {
            return bossEvent;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getIncrement() {
            return increment;
        }

        public void setIncrement(int increment) {
            this.increment = increment;
        }
    }
}

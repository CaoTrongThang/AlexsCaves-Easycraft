package com.github.alexmodguy.alexscaves.forge_shim.event;

import net.minecraft.world.entity.player.Player;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class TickEvent extends Event {
    public final Phase phase;

    public TickEvent(Phase phase) {
        this.phase = phase;
    }

    public enum Phase {
        START,
        END
    }

    public static class PlayerTickEvent extends TickEvent {
        public final Player player;

        public PlayerTickEvent(Player player, Phase phase) {
            super(phase);
            this.player = player;
        }
    }

    public static class ClientTickEvent extends TickEvent {
        public ClientTickEvent(Phase phase) {
            super(phase);
        }
    }
}

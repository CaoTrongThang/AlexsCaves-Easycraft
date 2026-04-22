package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

public class PlayerEvent extends Event {
    private final Player entity;

    public PlayerEvent(Player entity) {
        this.entity = entity;
    }

    public Player getEntity() {
        return entity;
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {
        public PlayerLoggedInEvent(Player entity) {
            super(entity);
        }
    }
}

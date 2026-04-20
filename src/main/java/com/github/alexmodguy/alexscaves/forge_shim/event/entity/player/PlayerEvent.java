package com.github.alexmodguy.alexscaves.forge_shim.event.entity.player;

import net.minecraft.world.entity.player.Player;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

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

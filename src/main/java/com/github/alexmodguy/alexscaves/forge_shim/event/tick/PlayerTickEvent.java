package com.github.alexmodguy.alexscaves.forge_shim.event.tick;

import net.minecraft.world.entity.player.Player;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class PlayerTickEvent extends Event {
    private final Player entity;

    public PlayerTickEvent(Player entity) {
        this.entity = entity;
    }

    public Player getEntity() {
        return entity;
    }

    public static class Post extends PlayerTickEvent {
        public Post(Player entity) {
            super(entity);
        }
    }
}

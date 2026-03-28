package net.minecraftforge.event.tick;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

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

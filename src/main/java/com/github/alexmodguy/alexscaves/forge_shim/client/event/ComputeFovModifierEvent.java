package com.github.alexmodguy.alexscaves.forge_shim.client.event;

import net.minecraft.world.entity.player.Player;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class ComputeFovModifierEvent extends Event {
    private final Player player;
    private final float fovModifier;
    private float newFovModifier;

    public ComputeFovModifierEvent(Player player, float fovModifier) {
        this.player = player;
        this.fovModifier = fovModifier;
        this.newFovModifier = fovModifier;
    }

    public Player getPlayer() {
        return player;
    }

    public float getFovModifier() {
        return fovModifier;
    }

    public float getNewFovModifier() {
        return newFovModifier;
    }

    public void setNewFovModifier(float newFovModifier) {
        this.newFovModifier = newFovModifier;
    }
}

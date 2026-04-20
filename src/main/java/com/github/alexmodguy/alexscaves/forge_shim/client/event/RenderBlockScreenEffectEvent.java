package com.github.alexmodguy.alexscaves.forge_shim.client.event;

import net.minecraft.world.entity.player.Player;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Cancelable;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

@Cancelable
public class RenderBlockScreenEffectEvent extends Event {
    private final Player player;
    private final OverlayType overlayType;

    public RenderBlockScreenEffectEvent(Player player, OverlayType overlayType) {
        this.player = player;
        this.overlayType = overlayType;
    }

    public Player getPlayer() {
        return player;
    }

    public OverlayType getOverlayType() {
        return overlayType;
    }

    public enum OverlayType {
        WATER
    }
}

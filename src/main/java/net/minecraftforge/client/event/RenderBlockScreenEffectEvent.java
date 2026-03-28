package net.minecraftforge.client.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

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

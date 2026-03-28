package net.neoforged.neoforge.event.village;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.bus.api.Event;

import java.util.List;

public class WandererTradesEvent extends Event {
    private final List<VillagerTrades.ItemListing> genericTrades;

    public WandererTradesEvent(List<VillagerTrades.ItemListing> genericTrades) {
        this.genericTrades = genericTrades;
    }

    public List<VillagerTrades.ItemListing> getGenericTrades() {
        return genericTrades;
    }
}

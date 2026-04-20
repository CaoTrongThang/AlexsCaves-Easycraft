package com.github.alexmodguy.alexscaves.forge_shim.event.village;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

import java.util.List;
import java.util.Map;

public class VillagerTradesEvent extends Event {
    private final VillagerProfession type;
    private final Map<Integer, List<VillagerTrades.ItemListing>> trades;

    public VillagerTradesEvent(VillagerProfession type, Map<Integer, List<VillagerTrades.ItemListing>> trades) {
        this.type = type;
        this.trades = trades;
    }

    public VillagerProfession getType() {
        return type;
    }

    public Map<Integer, List<VillagerTrades.ItemListing>> getTrades() {
        return trades;
    }
}

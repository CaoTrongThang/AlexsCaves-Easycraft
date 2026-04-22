package net.neoforged.neoforge.common.world.chunk;

import net.minecraft.core.BlockPos;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class TicketHelper {
    private final Map<UUID, TicketSet> entityTickets = new LinkedHashMap<>();
    private final Map<BlockPos, TicketSet> blockTickets = new LinkedHashMap<>();

    public Map<UUID, TicketSet> getEntityTickets() {
        return entityTickets;
    }

    public Map<BlockPos, TicketSet> getBlockTickets() {
        return blockTickets;
    }

    public void removeAllTickets(UUID owner) {
        entityTickets.remove(owner);
    }

    public void removeAllTickets(BlockPos owner) {
        blockTickets.remove(owner);
    }
}

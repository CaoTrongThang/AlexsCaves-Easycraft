package net.neoforged.neoforge.common.world.chunk;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.function.BiConsumer;

public class TicketController {
    public TicketController(ResourceLocation id) {
    }

    public TicketController(ResourceLocation id, BiConsumer<ServerLevel, TicketHelper> clearCallback) {
    }

    public void forceChunk(ServerLevel level, Object owner, int chunkX, int chunkZ, boolean add, boolean ticking) {
    }
}

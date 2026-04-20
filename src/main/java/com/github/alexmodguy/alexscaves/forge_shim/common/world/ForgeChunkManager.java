package com.github.alexmodguy.alexscaves.forge_shim.common.world;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public final class ForgeChunkManager {

    private ForgeChunkManager() {
    }

    public static void setForcedChunkLoadingCallback(String modId, BiConsumer<ServerLevel, TicketHelper> callback) {
    }

    public static void forceChunk(ServerLevel level, String modId, Object ticketOwner, int chunkX, int chunkZ, boolean add, boolean ticking) {
    }

    public static final class TicketHelper {
        public Map<UUID, Pair<LongSet, LongSet>> getEntityTickets() {
            return Collections.emptyMap();
        }

        public Map<BlockPos, Pair<LongSet, LongSet>> getBlockTickets() {
            return Collections.emptyMap();
        }

        public void removeAllTickets(Object owner) {
        }
    }
}

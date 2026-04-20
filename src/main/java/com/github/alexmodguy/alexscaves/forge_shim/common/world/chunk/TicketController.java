package com.github.alexmodguy.alexscaves.forge_shim.common.world.chunk;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class TicketController {
    private static final Set<TicketController> CONTROLLERS = Collections.newSetFromMap(new IdentityHashMap<>());
    private static boolean shutdownHookRegistered;

    private final Map<ServerLevel, Map<Object, Set<Long>>> ownedChunks = new HashMap<>();
    private final Map<ServerLevel, Map<Long, Integer>> chunkReferenceCounts = new HashMap<>();

    public TicketController(ResourceLocation id) {
        this(id, null);
    }

    public TicketController(ResourceLocation id, BiConsumer<ServerLevel, TicketHelper> clearCallback) {
        CONTROLLERS.add(this);
        registerShutdownHook();
    }

    public void forceChunk(ServerLevel level, Object owner, int chunkX, int chunkZ, boolean add, boolean ticking) {
        long chunkKey = ChunkPos.asLong(chunkX, chunkZ);
        Map<Object, Set<Long>> chunksByOwner = ownedChunks.computeIfAbsent(level, ignored -> new HashMap<>());
        Map<Long, Integer> refCounts = chunkReferenceCounts.computeIfAbsent(level, ignored -> new HashMap<>());
        Set<Long> ownerChunks = chunksByOwner.computeIfAbsent(owner, ignored -> new HashSet<>());

        if (add) {
            if (ownerChunks.add(chunkKey) && refCounts.merge(chunkKey, 1, Integer::sum) == 1) {
                level.setChunkForced(chunkX, chunkZ, true);
            }
            return;
        }

        if (!ownerChunks.remove(chunkKey)) {
            return;
        }

        if (ownerChunks.isEmpty()) {
            chunksByOwner.remove(owner);
        }

        int remaining = refCounts.getOrDefault(chunkKey, 0) - 1;
        if (remaining <= 0) {
            refCounts.remove(chunkKey);
            level.setChunkForced(chunkX, chunkZ, false);
        } else {
            refCounts.put(chunkKey, remaining);
        }

        if (chunksByOwner.isEmpty()) {
            ownedChunks.remove(level);
            chunkReferenceCounts.remove(level);
        }
    }

    private static void registerShutdownHook() {
        if (shutdownHookRegistered) {
            return;
        }
        shutdownHookRegistered = true;
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            for (TicketController controller : CONTROLLERS) {
                controller.releaseAll();
            }
        });
    }

    private void releaseAll() {
        for (Map.Entry<ServerLevel, Map<Long, Integer>> entry : chunkReferenceCounts.entrySet()) {
            ServerLevel level = entry.getKey();
            for (Long chunkKey : entry.getValue().keySet()) {
                level.setChunkForced(ChunkPos.getX(chunkKey), ChunkPos.getZ(chunkKey), false);
            }
        }
        ownedChunks.clear();
        chunkReferenceCounts.clear();
    }
}

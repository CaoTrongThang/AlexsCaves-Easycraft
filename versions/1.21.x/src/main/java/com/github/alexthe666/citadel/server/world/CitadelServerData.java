package com.github.alexthe666.citadel.server.world;

import com.github.alexthe666.citadel.server.tick.ServerTickRateTracker;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;

public class CitadelServerData extends SavedData {

    private static final String IDENTIFIER = "citadel_world_data";

    private final MinecraftServer server;
    private ServerTickRateTracker tickRateTracker = null;

    public CitadelServerData(MinecraftServer server) {
        super();
        this.server = server;
    }

    public static SavedDataType<CitadelServerData> type(MinecraftServer server) {
        return new SavedDataType<>(
            IDENTIFIER,
            context -> new CitadelServerData(server),
            context -> CompoundTag.CODEC.xmap(
                tag -> load(server, tag),
                data -> data.save(new CompoundTag(), server.overworld().registryAccess())
            ),
            null
        );
    }

    @Nonnull
    public static CitadelServerData get(MinecraftServer server) {
        DimensionDataStorage storage = server.getLevel(Level.OVERWORLD).getDataStorage();
        CitadelServerData data = storage.computeIfAbsent(type(server));
        data.setDirty();
        return data;
    }

    public static CitadelServerData load(MinecraftServer server, CompoundTag tag) {
        CitadelServerData data = new CitadelServerData(server);
        if (tag.contains("TickRateTracker")) {
            data.tickRateTracker = new ServerTickRateTracker(server, tag.getCompoundOrEmpty("TickRateTracker"));
        } else {
            data.tickRateTracker = new ServerTickRateTracker(server);
        }
        return data;
    }

    public ServerTickRateTracker getOrCreateTickRateTracker() {
        if (tickRateTracker == null) {
            tickRateTracker = new ServerTickRateTracker(server);
        }
        return tickRateTracker;
    }

    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        if (tickRateTracker != null) {
            tag.put("TickRateTracker", tickRateTracker.toTag());
        }
        return tag;
    }
}

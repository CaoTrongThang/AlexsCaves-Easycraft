package com.github.alexmodguy.alexscaves.forge_shim.fml;

import net.fabricmc.loader.api.FabricLoader;

public class ModList {
    private static final ModList INSTANCE = new ModList();

    public static ModList get() {
        return INSTANCE;
    }

    public boolean isLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}

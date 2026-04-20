package com.github.alexmodguy.alexscaves.forge_shim.fml.loading;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public enum FMLPaths {
    CONFIGDIR;

    public Path get() {
        return FabricLoader.getInstance().getConfigDir();
    }
}

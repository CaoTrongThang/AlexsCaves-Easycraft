package com.github.alexmodguy.alexscaves.forge_shim.fml.loading;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import com.github.alexmodguy.alexscaves.forge_shim.api.distmarker.Dist;

public class FMLEnvironment {
    public static final Dist dist = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT
        ? Dist.CLIENT
        : Dist.DEDICATED_SERVER;

    private FMLEnvironment() {
    }
}

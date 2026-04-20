package com.github.alexmodguy.alexscaves.forge_shim.fml.javafmlmod;

import com.github.alexmodguy.alexscaves.forge_shim.common.MinecraftForge;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.IEventBus;

public final class FMLJavaModLoadingContext {
    private static final FMLJavaModLoadingContext INSTANCE = new FMLJavaModLoadingContext();

    private FMLJavaModLoadingContext() {
    }

    public static FMLJavaModLoadingContext get() {
        return INSTANCE;
    }

    public IEventBus getModEventBus() {
        return MinecraftForge.EVENT_BUS;
    }
}

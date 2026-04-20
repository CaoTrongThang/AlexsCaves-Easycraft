package com.github.alexmodguy.alexscaves.forge_shim.common;

import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.IEventBus;

public final class MinecraftForge {
    public static final IEventBus EVENT_BUS = NeoForge.EVENT_BUS;

    private MinecraftForge() {
    }
}

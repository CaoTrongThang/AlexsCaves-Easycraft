package net.minecraftforge.common;

import net.minecraftforge.eventbus.api.IEventBus;

public final class MinecraftForge {
    public static final IEventBus EVENT_BUS = NeoForge.EVENT_BUS;

    private MinecraftForge() {
    }
}

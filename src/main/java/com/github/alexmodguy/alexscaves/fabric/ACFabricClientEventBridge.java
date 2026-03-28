package com.github.alexmodguy.alexscaves.fabric;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public final class ACFabricClientEventBridge {

    private ACFabricClientEventBridge() {
    }

    public static void registerClient() {
        ClientTickEvents.START_CLIENT_TICK.register(client ->
            MinecraftForge.EVENT_BUS.post(new TickEvent.ClientTickEvent(TickEvent.Phase.START))
        );
        ClientTickEvents.END_CLIENT_TICK.register(client ->
            MinecraftForge.EVENT_BUS.post(new TickEvent.ClientTickEvent(TickEvent.Phase.END))
        );
    }
}

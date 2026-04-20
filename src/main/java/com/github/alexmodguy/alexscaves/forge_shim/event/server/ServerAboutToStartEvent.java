package com.github.alexmodguy.alexscaves.forge_shim.event.server;

import net.minecraft.server.MinecraftServer;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class ServerAboutToStartEvent extends Event {
    private final MinecraftServer server;

    public ServerAboutToStartEvent(MinecraftServer server) {
        this.server = server;
    }

    public MinecraftServer getServer() {
        return server;
    }
}

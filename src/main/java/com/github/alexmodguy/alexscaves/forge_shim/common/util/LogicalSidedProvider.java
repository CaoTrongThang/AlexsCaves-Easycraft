package com.github.alexmodguy.alexscaves.forge_shim.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import com.github.alexmodguy.alexscaves.forge_shim.fml.LogicalSide;
import com.github.alexmodguy.alexscaves.forge_shim.server.ServerLifecycleHooks;

import java.util.function.Supplier;

public class LogicalSidedProvider<T> {
    public static final LogicalSidedProvider<MinecraftServer> WORKQUEUE = new LogicalSidedProvider<>();

    @SuppressWarnings("unchecked")
    public T get(LogicalSide side) {
        if (this == WORKQUEUE && side == LogicalSide.SERVER) {
            return (T) ServerLifecycleHooks.getCurrentServer();
        }
        return null;
    }

    public Supplier<MinecraftServer> getServer() {
        return ServerLifecycleHooks::getCurrentServer;
    }

    public Supplier<Minecraft> getClient() {
        return Minecraft::getInstance;
    }
}

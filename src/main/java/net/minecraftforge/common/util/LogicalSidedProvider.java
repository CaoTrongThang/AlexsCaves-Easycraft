package net.minecraftforge.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.server.ServerLifecycleHooks;

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

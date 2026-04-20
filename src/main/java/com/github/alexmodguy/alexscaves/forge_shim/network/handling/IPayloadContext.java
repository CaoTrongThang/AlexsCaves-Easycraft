package com.github.alexmodguy.alexscaves.forge_shim.network.handling;

import net.minecraft.world.entity.player.Player;

public interface IPayloadContext {
    Player player();

    PayloadFlow flow();

    default void enqueueWork(Runnable runnable) {
        runnable.run();
    }

    interface PayloadFlow {
        boolean isClientbound();
    }
}

package com.github.alexmodguy.alexscaves.forge_shim.eventbus.api;

public interface ICancellableEvent {
    default boolean isCanceled() {
        return false;
    }
}

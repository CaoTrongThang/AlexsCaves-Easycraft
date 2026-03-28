package net.minecraftforge.eventbus.api;

public interface ICancellableEvent {
    default boolean isCanceled() {
        return false;
    }
}

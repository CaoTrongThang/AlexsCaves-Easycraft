package net.minecraftforge.eventbus.api;

import java.util.function.Consumer;

public interface IEventBus {

    default <T> void addListener(Consumer<T> consumer) {
    }

    default void register(Object object) {
    }

    default boolean post(Event event) {
        return event.isCanceled();
    }
}

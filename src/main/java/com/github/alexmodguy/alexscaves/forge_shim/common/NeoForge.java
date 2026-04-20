package com.github.alexmodguy.alexscaves.forge_shim.common;

import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.IEventBus;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.SubscribeEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NeoForge {
    public static final IEventBus EVENT_BUS = new IEventBus() {
        private final Map<Class<?>, List<Consumer<Object>>> listeners = new HashMap<>();

        @Override
        public void register(Object object) {
            List<RegisteredHandler> registrations = new ArrayList<>();

            for (Method method : object.getClass().getMethods()) {
                SubscribeEvent subscribeEvent = method.getAnnotation(SubscribeEvent.class);
                if (subscribeEvent == null || method.getParameterCount() != 1) {
                    continue;
                }

                Class<?> eventType = method.getParameterTypes()[0];
                registrations.add(new RegisteredHandler(eventType, subscribeEvent.priority(), event -> {
                    try {
                        method.invoke(object, event);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to post " + event.getClass().getName() + " to " + method, e);
                    }
                }));
            }

            registrations.sort(Comparator.comparing((RegisteredHandler handler) -> handler.priority().ordinal()).reversed());
            for (RegisteredHandler registration : registrations) {
                listeners.computeIfAbsent(registration.eventType(), ignored -> new ArrayList<>()).add(registration.consumer());
            }
        }

        @Override
        public <T> void addListener(Consumer<T> consumer) {
            // Not needed for this hack, since all usages are @SubscribeEvent
        }

        @Override
        public boolean post(Event event) {
            List<Consumer<Object>> list = listeners.get(event.getClass());
            if (list != null) {
                for (Consumer<Object> consumer : list) {
                    consumer.accept(event);
                }
            }
            // Also check superclasses if needed
            Class<?> superclass = event.getClass().getSuperclass();
            while (superclass != null && superclass != Object.class) {
                List<Consumer<Object>> superList = listeners.get(superclass);
                if (superList != null) {
                    for (Consumer<Object> consumer : superList) {
                        consumer.accept(event);
                    }
                }
                superclass = superclass.getSuperclass();
            }
            return event.isCanceled();
        }
    };

    private record RegisteredHandler(Class<?> eventType, com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.EventPriority priority, Consumer<Object> consumer) {
    }

    private NeoForge() {
    }
}

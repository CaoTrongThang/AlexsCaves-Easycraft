package net.neoforged.neoforge.common;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NeoForge {
    public static final IEventBus EVENT_BUS = new IEventBus() {
        private final Map<Class<?>, List<Consumer<Object>>> listeners = new HashMap<>();

        @Override
        public void register(Object object) {
            for (Method method : object.getClass().getMethods()) {
                if (method.isAnnotationPresent(SubscribeEvent.class) && method.getParameterCount() == 1) {
                    Class<?> eventType = method.getParameterTypes()[0];
                    listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(event -> {
                        try {
                            method.invoke(object, event);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }

        @Override
        public <T> void addListener(Consumer<T> consumer) {
            // Not needed for this hack, since all usages are @SubscribeEvent
        }

        @Override
        public <T> T post(T event) {
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
            return event;
        }
    };

    private NeoForge() {
    }
}

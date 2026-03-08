package net.neoforged.fml.util;

import java.lang.reflect.Field;

public class ObfuscationReflectionHelper {

    public static Field findField(Class<?> type, String name) {
        Class<?> current = type;
        while (current != null) {
            try {
                Field field = current.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        throw new IllegalArgumentException("Unable to find field " + name + " on " + type.getName());
    }

    private ObfuscationReflectionHelper() {
    }
}

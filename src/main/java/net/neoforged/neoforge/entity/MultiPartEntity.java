package net.neoforged.neoforge.entity;

import org.jetbrains.annotations.Nullable;

public interface MultiPartEntity {

    default boolean isMultipartEntity() {
        return false;
    }

    @Nullable
    default PartEntity<?>[] getParts() {
        return null;
    }
}

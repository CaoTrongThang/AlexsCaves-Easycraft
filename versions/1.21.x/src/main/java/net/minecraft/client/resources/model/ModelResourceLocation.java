package net.minecraft.client.resources.model;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ModelResourceLocation {
    private final ResourceLocation id;
    private final String variant;

    public ModelResourceLocation(ResourceLocation id, String variant) {
        this.id = id;
        this.variant = variant;
    }

    public static ModelResourceLocation vanilla(String path, String variant) {
        return new ModelResourceLocation(ResourceLocation.withDefaultNamespace(path), variant);
    }

    public ResourceLocation id() {
        return id;
    }

    public String variant() {
        return variant;
    }

    @Override
    public String toString() {
        return id + "#" + variant;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ModelResourceLocation other)) {
            return false;
        }
        return Objects.equals(id, other.id) && Objects.equals(variant, other.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, variant);
    }
}

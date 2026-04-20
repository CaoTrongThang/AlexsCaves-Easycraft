package com.github.alexmodguy.alexscaves.forge_shim.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class RegistryObject<T> extends DeferredHolder<T, T> {

    RegistryObject(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation id, Supplier<? extends T> factory) {
        super(registryKey, id, factory);
    }
}

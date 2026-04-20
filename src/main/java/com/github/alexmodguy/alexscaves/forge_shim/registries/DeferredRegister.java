package com.github.alexmodguy.alexscaves.forge_shim.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeferredRegister<T> {

    protected final ResourceKey<? extends Registry<T>> registryKey;
    protected final String namespace;
    protected final List<RegistryObject<? extends T>> entries = new ArrayList<>();

    protected DeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        this.registryKey = registryKey;
        this.namespace = namespace;
    }

    public static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        return new DeferredRegister<>(registryKey, namespace);
    }

    public static <T> DeferredRegister<T> create(Registry<T> registry, String namespace) {
        return new DeferredRegister<>(registry.key(), namespace);
    }

    public static <T> DeferredRegister<T> create(ForgeRegistries.RegistryView<T> registryView, String namespace) {
        return new DeferredRegister<>(registryView.key(), namespace);
    }

    public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
        @SuppressWarnings("unchecked")
        RegistryObject<I> holder = new RegistryObject<>((ResourceKey<? extends Registry<I>>) (ResourceKey<?>) registryKey, new ResourceLocation(namespace, name), supplier);
        entries.add(holder);
        return holder;
    }

    public List<RegistryObject<? extends T>> getEntries() {
        return entries;
    }

    public void register(IEventBus eventBus) {
        entries.forEach(RegistryObject::resolve);
    }
}

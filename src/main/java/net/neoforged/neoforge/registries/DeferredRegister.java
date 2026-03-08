package net.neoforged.neoforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeferredRegister<T> {

    protected final ResourceKey<? extends Registry<T>> registryKey;
    protected final String namespace;
    protected final List<DeferredHolder<T, ? extends T>> entries = new ArrayList<>();

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

    public static DataComponents createDataComponents(ResourceKey<? extends Registry<DataComponentType<?>>> registryKey, String namespace) {
        return new DataComponents(registryKey, namespace);
    }

    public <I extends T> DeferredHolder<T, I> register(String name, Supplier<? extends I> supplier) {
        DeferredHolder<T, I> holder = new DeferredHolder<>(registryKey, ResourceLocation.fromNamespaceAndPath(namespace, name), supplier);
        entries.add(holder);
        return holder;
    }

    public void register(IEventBus eventBus) {
        entries.forEach(DeferredHolder::resolve);
    }

    public static class DataComponents extends DeferredRegister<DataComponentType<?>> {
        private DataComponents(ResourceKey<? extends Registry<DataComponentType<?>>> registryKey, String namespace) {
            super(registryKey, namespace);
        }
    }
}

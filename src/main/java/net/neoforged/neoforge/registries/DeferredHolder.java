package net.neoforged.neoforge.registries;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DeferredHolder<R, T extends R> implements Supplier<T>, Holder<T> {

    private final ResourceKey<? extends Registry<R>> registryKey;
    private final ResourceLocation id;
    private final Supplier<? extends T> factory;
    private T value;
    private Holder.Reference<T> holder;

    DeferredHolder(ResourceKey<? extends Registry<R>> registryKey, ResourceLocation id, Supplier<? extends T> factory) {
        this.registryKey = registryKey;
        this.id = id;
        this.factory = factory;
    }

    @Override
    public T get() {
        return resolve();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public synchronized T resolve() {
        if (value != null) {
            return value;
        }

        Registry<T> registry = getRegistry();
        if (registry != null && registry.containsKey(getId())) {
            value = registry.get(getId());
            holder = registry.getHolder(getId()).orElse(null);
            if (value != null) {
                return value;
            }
        }

        value = factory.get();
        if (registry != null) {
            if (registry.containsKey(getId())) {
                holder = registry.getHolder(getId()).orElse(null);
            } else {
                holder = Registry.registerForHolder(registry, getId(), value);
            }
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private Registry<T> getRegistry() {
        return (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location());
    }

    private Holder<T> getRegisteredHolder() {
        if (holder != null) {
            return holder;
        }
        Registry<T> registry = getRegistry();
        if (registry == null) {
            return null;
        }
        holder = registry.getHolder(getId()).orElse(null);
        return holder;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResourceKey<T> getId() {
        return ResourceKey.create((ResourceKey) registryKey, id);
    }

    public ResourceKey<T> getKey() {
        return getId();
    }

    @Override
    public T value() {
        return resolve();
    }

    @Override
    public boolean isBound() {
        return getRegisteredHolder() != null || value != null;
    }

    @Override
    public boolean is(ResourceLocation location) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.is(location) : id.equals(location);
    }

    @Override
    public boolean is(ResourceKey<T> resourceKey) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.is(resourceKey) : getId().equals(resourceKey);
    }

    @Override
    public boolean is(Predicate<ResourceKey<T>> predicate) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.is(predicate) : predicate.test(getId());
    }

    @Override
    public boolean is(TagKey<T> tagKey) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null && registeredHolder.is(tagKey);
    }

    @Override
    public boolean is(Holder<T> holder) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.is(holder) : holder == this || holder.value() == value();
    }

    @Override
    public Stream<TagKey<T>> tags() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.tags() : Stream.empty();
    }

    @Override
    public Either<ResourceKey<T>, T> unwrap() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.unwrap() : Either.left(getId());
    }

    @Override
    public Optional<ResourceKey<T>> unwrapKey() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.unwrapKey() : Optional.of(getId());
    }

    @Override
    public Kind kind() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.kind() : Kind.REFERENCE;
    }

    @Override
    public boolean canSerializeIn(HolderOwner<T> owner) {
        Holder<T> registeredHolder = getRegisteredHolder();
        if (registeredHolder != null && registeredHolder.canSerializeIn(owner)) {
            return true;
        }
        Registry<T> registry = getRegistry();
        return registry != null && registry.containsKey(getId());
    }
}

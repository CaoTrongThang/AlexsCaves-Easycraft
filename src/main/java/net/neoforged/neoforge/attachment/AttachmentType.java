package net.neoforged.neoforge.attachment;

import java.util.function.Supplier;

public class AttachmentType<T> {
    private final Supplier<T> factory;

    private AttachmentType(Supplier<T> factory) {
        this.factory = factory;
    }

    public T createDefault() {
        return factory.get();
    }

    public static <T> Builder<T> builder(Supplier<T> factory) {
        return new Builder<>(factory);
    }

    public static class Builder<T> {
        private final Supplier<T> factory;

        private Builder(Supplier<T> factory) {
            this.factory = factory;
        }

        public Builder<T> serialize(Object codec) {
            return this;
        }

        public Builder<T> copyOnDeath() {
            return this;
        }

        public AttachmentType<T> build() {
            return new AttachmentType<>(factory);
        }
    }
}

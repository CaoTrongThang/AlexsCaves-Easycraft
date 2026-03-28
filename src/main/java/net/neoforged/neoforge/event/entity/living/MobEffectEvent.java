package net.neoforged.neoforge.event.entity.living;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectEvent extends LivingEvent {
    public MobEffectEvent(LivingEntity entity) {
        super(entity);
    }

    public static class Remove extends MobEffectEvent {
        private final Holder<MobEffect> effect;

        public Remove(LivingEntity entity, Holder<MobEffect> effect) {
            super(entity);
            this.effect = effect;
        }

        public Holder<MobEffect> getEffect() {
            return effect;
        }
    }

    public static class Added extends MobEffectEvent {
        private final MobEffectInstance effectInstance;

        public Added(LivingEntity entity, MobEffectInstance effectInstance) {
            super(entity);
            this.effectInstance = effectInstance;
        }

        public MobEffectInstance getEffectInstance() {
            return effectInstance;
        }
    }

    public static class Expired extends MobEffectEvent {
        private final MobEffectInstance effectInstance;

        public Expired(LivingEntity entity, MobEffectInstance effectInstance) {
            super(entity);
            this.effectInstance = effectInstance;
        }

        public MobEffectInstance getEffectInstance() {
            return effectInstance;
        }
    }
}

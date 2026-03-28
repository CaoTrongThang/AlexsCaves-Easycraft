package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingDamageEvent extends LivingEvent {
    private final DamageSource source;

    public LivingDamageEvent(LivingEntity entity, DamageSource source) {
        super(entity);
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }

    public static class Pre extends LivingDamageEvent {
        private float newDamage;

        public Pre(LivingEntity entity, DamageSource source, float newDamage) {
            super(entity, source);
            this.newDamage = newDamage;
        }

        public float getNewDamage() {
            return newDamage;
        }

        public void setNewDamage(float newDamage) {
            this.newDamage = newDamage;
        }
    }
}

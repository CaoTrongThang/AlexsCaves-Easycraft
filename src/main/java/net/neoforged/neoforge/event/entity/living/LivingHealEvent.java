package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.entity.LivingEntity;

public class LivingHealEvent extends LivingEvent {
    private boolean canceled;

    public LivingHealEvent(LivingEntity entity) {
        super(entity);
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}

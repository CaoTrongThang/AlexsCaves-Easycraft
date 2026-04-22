package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class LivingChangeTargetEvent extends LivingEvent {
    private final LivingEntity newAboutToBeSetTarget;
    private boolean canceled;

    public LivingChangeTargetEvent(LivingEntity entity, @Nullable LivingEntity newAboutToBeSetTarget) {
        super(entity);
        this.newAboutToBeSetTarget = newAboutToBeSetTarget;
    }

    @Nullable
    public LivingEntity getNewAboutToBeSetTarget() {
        return newAboutToBeSetTarget;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}

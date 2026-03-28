package net.minecraftforge.event.entity.living;

import net.minecraft.world.entity.Mob;

public class FinalizeSpawnEvent extends LivingEvent {
    public FinalizeSpawnEvent(Mob entity) {
        super(entity);
    }
}

package com.github.alexmodguy.alexscaves.forge_shim.event.entity.living;

import net.minecraft.world.entity.Mob;

public class FinalizeSpawnEvent extends LivingEvent {
    public FinalizeSpawnEvent(Mob entity) {
        super(entity);
    }
}

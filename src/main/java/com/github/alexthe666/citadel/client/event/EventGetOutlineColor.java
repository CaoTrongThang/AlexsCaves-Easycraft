package com.github.alexthe666.citadel.client.event;

import net.minecraft.world.entity.Entity;
import com.github.alexmodguy.alexscaves.forge_shim.api.distmarker.Dist;
import com.github.alexmodguy.alexscaves.forge_shim.api.distmarker.OnlyIn;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

@OnlyIn(Dist.CLIENT)
@Event.HasResult
public class EventGetOutlineColor extends Event {
    private Entity entityIn;
    private int color;

    public EventGetOutlineColor(Entity entityIn, int color) {
        this.entityIn = entityIn;
        this.color = color;
    }

    public Entity getEntityIn() {
        return entityIn;
    }

    public void setEntityIn(Entity entityIn) {
        this.entityIn = entityIn;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}

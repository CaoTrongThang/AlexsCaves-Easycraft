package com.github.alexmodguy.alexscaves.server.entity.ai;

import com.github.alexmodguy.alexscaves.server.entity.living.DinosaurEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class DinosaurWanderGoal extends RandomStrollGoal {
    private final DinosaurEntity dinosaur;

    public DinosaurWanderGoal(DinosaurEntity dinosaur, double speed, int chance) {
        super(dinosaur, speed, chance);
        this.dinosaur = dinosaur;
    }

    @Override
    @Nullable
    protected Vec3 getPosition() {
        if (dinosaur.isTame() && dinosaur.getCommand() == 0 && dinosaur.hasRestriction()) {
            return DefaultRandomPos.getPosTowards(this.mob, 16, 7, Vec3.atCenterOf(dinosaur.getRestrictCenter()),
                    (double) ((float) Math.PI / 2F));
        }
        return super.getPosition();
    }
}

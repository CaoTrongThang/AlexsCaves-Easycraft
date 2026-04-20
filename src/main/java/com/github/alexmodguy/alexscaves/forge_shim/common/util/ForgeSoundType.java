package com.github.alexmodguy.alexscaves.forge_shim.common.util;

import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ForgeSoundType extends DeferredSoundType {
    public ForgeSoundType(float volume, float pitch, Supplier<SoundEvent> breakSound, Supplier<SoundEvent> stepSound, Supplier<SoundEvent> placeSound, Supplier<SoundEvent> hitSound, Supplier<SoundEvent> fallSound) {
        super(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
    }
}

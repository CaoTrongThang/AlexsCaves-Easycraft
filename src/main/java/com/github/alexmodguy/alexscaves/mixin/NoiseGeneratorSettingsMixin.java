package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexthe666.citadel.server.generation.SurfaceRulesManager;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NoiseGeneratorSettings.class, priority = 1000)
public abstract class NoiseGeneratorSettingsMixin {

    @Shadow
    public abstract SurfaceRules.RuleSource surfaceRule();

    @Inject(method = "surfaceRule", at = @At("RETURN"), cancellable = true)
    private void ac_surfaceRule(CallbackInfoReturnable<SurfaceRules.RuleSource> cir) {
        // We assume that if this is being called, we want to inject our rules if they
        // exist.
        // Citadel's SurfaceRulesManager.mergeOverworldRules handles the merging logic.
        // It prepends the modded rules to the sequence.
        cir.setReturnValue(SurfaceRulesManager.mergeOverworldRules(cir.getReturnValue()));
    }
}

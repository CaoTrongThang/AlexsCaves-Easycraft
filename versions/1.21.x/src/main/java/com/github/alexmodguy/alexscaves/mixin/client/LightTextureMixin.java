package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.server.entity.util.PossessesCamera;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.potion.DeepsightEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LightTexture.class, priority = -100)
public abstract class LightTextureMixin {

    @Inject(
        method = "Lnet/minecraft/client/renderer/LightTexture;getBrightness(Lnet/minecraft/world/level/dimension/DimensionType;I)F",
        at = @At("RETURN"),
        cancellable = true
    )
    private static void ac_getBrightness(DimensionType dimensionType, int lightTextureIndex, CallbackInfoReturnable<Float> cir) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!AlexsCaves.CLIENT_CONFIG.biomeAmbientLight.get() || minecraft.player == null) {
            return;
        }
        float partialTick = minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        float ambientAmount = ClientProxy.lastBiomeAmbientLightAmountPrev + (ClientProxy.lastBiomeAmbientLightAmount - ClientProxy.lastBiomeAmbientLightAmountPrev) * partialTick;
        float primordialBossAmount = AlexsCaves.PROXY.getPrimordialBossActiveAmount(partialTick);
        if (minecraft.getCameraEntity() instanceof PossessesCamera || minecraft.getCameraEntity() instanceof LivingEntity afflicted && afflicted.hasEffect(ACEffectRegistry.DARKNESS_INCARNATE)) {
            ambientAmount = Math.max(ambientAmount, 0.35F);
        }
        if (minecraft.player.hasEffect(ACEffectRegistry.DEEPSIGHT) && minecraft.player.isUnderWater()) {
            ambientAmount = Math.min(1.0F, ambientAmount + 0.05F * DeepsightEffect.getIntensity(minecraft.player, partialTick));
        }
        float light = ambientAmount + cir.getReturnValue();
        if (primordialBossAmount > 0.0F) {
            cir.setReturnValue(Math.max(0.0F, light - primordialBossAmount * 0.06F));
        } else if (ambientAmount != 0.0F) {
            cir.setReturnValue(light);
        }
    }
}

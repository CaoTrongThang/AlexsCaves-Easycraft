package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.server.entity.util.PossessesCamera;
import com.github.alexmodguy.alexscaves.server.misc.ACLoadedMods;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.github.alexmodguy.alexscaves.server.potion.DeepsightEffect;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LightTexture.class, priority = -100)
public abstract class LightTextureMixin {

    @Shadow
    @Final
    private NativeImage lightPixels;
    @Shadow
    private boolean updateLightTexture;
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract float getDarknessGamma(float p_234320_);

    @Shadow
    protected abstract float calculateDarknessScale(LivingEntity p_234313_, float p_234314_, float p_234315_);

    @Shadow
    public static float getBrightness(DimensionType p_234317_, int p_234318_) {
        return 0;
    }

    @Shadow
    private float blockLightRedFlicker;

    @Shadow
    private static void clampColor(Vector3f p_254122_) {
    }

    @Shadow
    @Final
    private GameRenderer renderer;

    @Shadow
    protected abstract float notGamma(float p_109893_);

    @Shadow
    @Final
    private DynamicTexture lightTexture;

    @Inject(method = {
            "Lnet/minecraft/client/renderer/LightTexture;getBrightness(Lnet/minecraft/world/level/dimension/DimensionType;I)F" }, remap = true, cancellable = true, at = @At(value = "TAIL"))
    private static void ac_getBrightness(DimensionType dimensionType, int lightTextureIndex,
            CallbackInfoReturnable<Float> cir) {
        if (AlexsCaves.CLIENT_CONFIG.biomeAmbientLight.get()) {
            float f = ClientProxy.lastBiomeAmbientLightAmountPrev
                    + (ClientProxy.lastBiomeAmbientLightAmount - ClientProxy.lastBiomeAmbientLightAmountPrev)
                            * Minecraft.getInstance().getFrameTime();
            float primordialBossAmount = AlexsCaves.PROXY
                    .getPrimordialBossActiveAmount(Minecraft.getInstance().getFrameTime());
            if (Minecraft.getInstance().getCameraEntity() instanceof PossessesCamera
                    || Minecraft.getInstance().getCameraEntity() instanceof LivingEntity afflicted
                            && afflicted.hasEffect(ACEffectRegistry.DARKNESS_INCARNATE.get())) {
                f = Math.max(f, 0.35F);
            }
            if (Minecraft.getInstance().player.hasEffect(ACEffectRegistry.DEEPSIGHT.get())
                    && Minecraft.getInstance().player.isUnderWater()) {
                f = Math.min(1.0F, f + 0.05F * DeepsightEffect.getIntensity(Minecraft.getInstance().player,
                        Minecraft.getInstance().getFrameTime()));
            }
            float light = f + cir.getReturnValue();
            if (primordialBossAmount > 0.0F) {
                cir.setReturnValue(Math.max(0.0F, light - primordialBossAmount * 0.06F));
            } else if (f != 0) {
                cir.setReturnValue(light);
            }
        }
    }

    @Inject(method = {
            "Lnet/minecraft/client/renderer/LightTexture;updateLightTexture(F)V" }, remap = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;upload()V"))
    private void ac_postUpdateLightTexture(float partialTicks, CallbackInfo ci) {
        if (AlexsCaves.CLIENT_CONFIG.biomeAmbientLightColoring.get() && !ACLoadedMods.isDistantHorizonsLoaded()) {
            ClientLevel clientlevel = this.minecraft.level;
            if (clientlevel != null) {
                for (int i = 0; i < 16; ++i) {
                    for (int j = 0; j < 16; ++j) {
                        if (i == 15 && j == 15) {
                            continue;
                        }
                        int color = this.lightPixels.getPixelRGBA(j, i);
                        Vector3f vector3f = new Vector3f((color & 0xFF) / 255.0F, ((color >> 8) & 0xFF) / 255.0F,
                                ((color >> 16) & 0xFF) / 255.0F);
                        this.applyACLightingColors(clientlevel, vector3f);
                        int k = (int) (vector3f.x * 255.0F);
                        int l = (int) (vector3f.y * 255.0F);
                        int i1 = (int) (vector3f.z * 255.0F);
                        this.lightPixels.setPixelRGBA(j, i, -16777216 | i1 << 16 | l << 8 | k);
                    }
                }
                this.lightPixels.setPixelRGBA(15, 15, -1);
            }
        }
    }

    private void applyACLightingColors(ClientLevel clientLevel, Vector3f vector3f) {
        if (!clientLevel.effects().forceBrightLightmap()) {
            Vec3 in = new Vec3(vector3f);
            Vec3 to = ClientProxy.lastBiomeLightColorPrev.add(ClientProxy.lastBiomeLightColor
                    .subtract(ClientProxy.lastBiomeLightColorPrev).scale(Minecraft.getInstance().getFrameTime()));
            vector3f.set(to.x * in.x, to.y * in.y, to.z * in.z);
        }
    }

}

package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.client.sound.ACMusics;
import com.github.alexmodguy.alexscaves.server.entity.util.PossessesCamera;
import com.github.alexmodguy.alexscaves.server.misc.ACTagRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Mixin(value = Minecraft.class, priority = -100)
public abstract class MinecraftMixin {

    @Shadow
    @Nullable
    public abstract Entity getCameraEntity();

    @Shadow @Nullable public LocalPlayer player;

    @Shadow @Final public Gui gui;

    @Inject(method = "Lnet/minecraft/client/Minecraft;startAttack()Z",
            at = @At("HEAD"),
            cancellable = true)
    private void ac_startAttack(CallbackInfoReturnable<Boolean> cir) {
        if (getCameraEntity() instanceof PossessesCamera) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "Lnet/minecraft/client/Minecraft;startUseItem()V",
            at = @At("HEAD"),
            cancellable = true)
    private void ac_startUseItem(CallbackInfo ci) {
        if (getCameraEntity() instanceof PossessesCamera) {
            ci.cancel();
        }
    }

    @Inject(method = "getSituationalMusic",
            at = @At("HEAD"),
            cancellable = true,
            require = 0)
    private void ac_getSituationalMusic(CallbackInfoReturnable<Object> cir) {
        Music music = null;
        if (this.player != null) {
            if (this.gui.getBossOverlay() != null && this.gui.getBossOverlay().shouldPlayMusic() && ClientProxy.primordialBossActive) {
                music = ACMusics.LUXTRUCTOSAURUS_BOSS_MUSIC;
            } else {
                Holder<Biome> holder = this.player.level().getBiome(this.player.blockPosition());
                if (holder.is(ACTagRegistry.OVERRIDE_ALL_VANILLA_MUSIC_IN)) {
                    music = holder.value().getBackgroundMusic().orElse(Musics.GAME);
                }
            }
        }
        Object wrappedMusic = ac_wrapSituationalMusic(music);
        if (wrappedMusic != null) {
            cir.setReturnValue(wrappedMusic);
        }
    }

    @Nullable
    private static Object ac_wrapSituationalMusic(@Nullable Music music) {
        if (music == null) {
            return null;
        }
        try {
            Method method = ac_findSituationalMusicMethod();
            if (method == null) {
                return music;
            }
            Class<?> returnType = method.getReturnType();
            if (returnType.isInstance(music)) {
                return music;
            }
            for (Constructor<?> constructor : returnType.getDeclaredConstructors()) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(music.getClass())) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(music);
                }
            }
        } catch (ReflectiveOperationException ignored) {
        }
        return music;
    }

    @Nullable
    private static Method ac_findSituationalMusicMethod() {
        for (Method method : Minecraft.class.getDeclaredMethods()) {
            if (method.getParameterCount() == 0 && (method.getName().equals("getSituationalMusic") || method.getName().equals("method_1544"))) {
                return method;
            }
        }
        return null;
    }
}

package com.github.alexmodguy.alexscaves.mixin.client;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LevelRenderer.class, priority = 800)
public abstract class LevelRendererMixin {
}

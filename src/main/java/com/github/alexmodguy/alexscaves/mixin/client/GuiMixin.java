package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.forge_shim.client.event.RenderGuiOverlayEvent;
import com.github.alexmodguy.alexscaves.forge_shim.client.gui.overlay.VanillaGuiOverlay;
import com.github.alexmodguy.alexscaves.forge_shim.common.MinecraftForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    @Final
    protected Minecraft minecraft;

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void ac_beforePlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        RenderGuiOverlayEvent.Pre event = new RenderGuiOverlayEvent.Pre(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.PLAYER_HEALTH);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderPlayerHealth", at = @At("TAIL"))
    private void ac_afterPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        RenderGuiOverlayEvent.Post event = new RenderGuiOverlayEvent.Post(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.PLAYER_HEALTH);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void ac_beforeCrosshair(GuiGraphics guiGraphics, CallbackInfo ci) {
        RenderGuiOverlayEvent.Pre event = new RenderGuiOverlayEvent.Pre(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.CROSSHAIR);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void ac_afterCrosshair(GuiGraphics guiGraphics, CallbackInfo ci) {
        RenderGuiOverlayEvent.Post event = new RenderGuiOverlayEvent.Post(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.CROSSHAIR);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void ac_beforeExperienceBar(GuiGraphics guiGraphics, int x, CallbackInfo ci) {
        RenderGuiOverlayEvent.Pre event = new RenderGuiOverlayEvent.Pre(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.EXPERIENCE_BAR);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderExperienceBar", at = @At("TAIL"))
    private void ac_afterExperienceBar(GuiGraphics guiGraphics, int x, CallbackInfo ci) {
        RenderGuiOverlayEvent.Post event = new RenderGuiOverlayEvent.Post(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.EXPERIENCE_BAR);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Inject(method = "renderJumpMeter", at = @At("HEAD"), cancellable = true)
    private void ac_beforeJumpBar(net.minecraft.world.entity.PlayerRideableJumping playerRideableJumping,
            GuiGraphics guiGraphics, int x, CallbackInfo ci) {
        RenderGuiOverlayEvent.Pre event = new RenderGuiOverlayEvent.Pre(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.JUMP_BAR);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderJumpMeter", at = @At("TAIL"))
    private void ac_afterJumpBar(net.minecraft.world.entity.PlayerRideableJumping playerRideableJumping,
            GuiGraphics guiGraphics, int x, CallbackInfo ci) {
        RenderGuiOverlayEvent.Post event = new RenderGuiOverlayEvent.Post(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.JUMP_BAR);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Inject(method = "renderSelectedItemName", at = @At("HEAD"), cancellable = true)
    private void ac_beforeItemName(GuiGraphics guiGraphics, CallbackInfo ci) {
        RenderGuiOverlayEvent.Pre event = new RenderGuiOverlayEvent.Pre(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.ITEM_NAME);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderSelectedItemName", at = @At("TAIL"))
    private void ac_afterItemName(GuiGraphics guiGraphics, CallbackInfo ci) {
        RenderGuiOverlayEvent.Post event = new RenderGuiOverlayEvent.Post(guiGraphics, this.minecraft.getWindow(),
                VanillaGuiOverlay.ITEM_NAME);
        MinecraftForge.EVENT_BUS.post(event);
    }
}

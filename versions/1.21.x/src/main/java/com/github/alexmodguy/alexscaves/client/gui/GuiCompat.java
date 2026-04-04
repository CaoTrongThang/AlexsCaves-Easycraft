package com.github.alexmodguy.alexscaves.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.util.context.ContextMap;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Optional;

public final class GuiCompat {

    public static final ContextMap EMPTY_CONTEXT = new ContextMap.Builder().create(new ContextKeySet.Builder().build());

    private GuiCompat() {
    }

    public static float gamePartialTick() {
        return Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
    }

    public static float gameDeltaTicks() {
        return Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaTicks();
    }

    public static float realtimeDeltaTicks() {
        return Minecraft.getInstance().getDeltaTracker().getRealtimeDeltaTicks();
    }

    public static void blit(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int width, int height) {
        guiGraphics.blit(texture, x, y, width, height, 0.0F, 1.0F, 0.0F, 1.0F);
    }

    public static void blit(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int u, int v, int width, int height) {
        blit(guiGraphics, texture, x, y, u, v, width, height, 256, 256);
    }

    public static void blit(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        float u0 = u / (float) textureWidth;
        float u1 = (u + width) / (float) textureWidth;
        float v0 = v / (float) textureHeight;
        float v1 = (v + height) / (float) textureHeight;
        guiGraphics.blit(texture, x, y, width, height, u0, u1, v0, v1);
    }

    public static void setTooltip(GuiGraphics guiGraphics, Font font, List<Component> lines, int mouseX, int mouseY) {
        guiGraphics.setTooltipForNextFrame(font, lines, Optional.empty(), mouseX, mouseY);
    }

    public static Matrix4f poseToMatrix4f(Matrix3x2fc pose) {
        return new Matrix4f(
            pose.m00(), pose.m01(), 0.0F, 0.0F,
            pose.m10(), pose.m11(), 0.0F, 0.0F,
            0.0F, 0.0F, 1.0F, 0.0F,
            pose.m20(), pose.m21(), 0.0F, 1.0F
        );
    }
}

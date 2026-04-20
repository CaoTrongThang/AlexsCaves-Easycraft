package com.github.alexmodguy.alexscaves.forge_shim.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public class RenderNameTagEvent extends Event {
    private final Entity entity;
    private Component content;
    private final EntityRenderer<?> renderer;
    private final PoseStack poseStack;
    private final MultiBufferSource multiBufferSource;
    private final int packedLight;
    private final float partialTick;

    public RenderNameTagEvent(Entity entity, Component content, EntityRenderer<?> renderer, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, float partialTick) {
        this.entity = entity;
        this.content = content;
        this.renderer = renderer;
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
        this.packedLight = packedLight;
        this.partialTick = partialTick;
    }

    public Entity getEntity() {
        return entity;
    }

    public Component getContent() {
        return content;
    }

    public void setContent(Component content) {
        this.content = content;
    }

    public EntityRenderer<?> getRenderer() {
        return renderer;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public MultiBufferSource getMultiBufferSource() {
        return multiBufferSource;
    }

    public int getPackedLight() {
        return packedLight;
    }

    public float getPartialTick() {
        return partialTick;
    }
}

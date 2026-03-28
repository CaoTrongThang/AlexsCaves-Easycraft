package net.minecraftforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraftforge.eventbus.api.Event;

public class RenderLevelStageEvent extends Event {
    private final Stage stage;
    private final LevelRenderer levelRenderer;
    private final PoseStack poseStack;
    private final int renderTick;
    private final Camera camera;
    private final float partialTick;

    public RenderLevelStageEvent(Stage stage, LevelRenderer levelRenderer, PoseStack poseStack, int renderTick, Camera camera, float partialTick) {
        this.stage = stage;
        this.levelRenderer = levelRenderer;
        this.poseStack = poseStack;
        this.renderTick = renderTick;
        this.camera = camera;
        this.partialTick = partialTick;
    }

    public Stage getStage() {
        return stage;
    }

    public LevelRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public int getRenderTick() {
        return renderTick;
    }

    public Camera getCamera() {
        return camera;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public enum Stage {
        AFTER_SKY,
        AFTER_ENTITIES,
        AFTER_CUTOUT_BLOCKS,
        AFTER_TRANSLUCENT_BLOCKS
    }
}

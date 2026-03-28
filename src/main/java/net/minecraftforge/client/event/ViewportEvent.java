package net.minecraftforge.client.event;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class ViewportEvent extends Event {
    private final Camera camera;
    private final double partialTick;

    protected ViewportEvent(Camera camera, double partialTick) {
        this.camera = camera;
        this.partialTick = partialTick;
    }

    public Camera getCamera() {
        return camera;
    }

    public double getPartialTick() {
        return partialTick;
    }

    public static class ComputeCameraAngles extends ViewportEvent {
        private float roll;

        public ComputeCameraAngles(Camera camera, double partialTick, float roll) {
            super(camera, partialTick);
            this.roll = roll;
        }

        public float getRoll() {
            return roll;
        }

        public void setRoll(float roll) {
            this.roll = roll;
        }
    }

    @Cancelable
    public static class RenderFog extends ViewportEvent {
        private final FogRenderer.FogMode mode;
        private float nearPlaneDistance;
        private float farPlaneDistance;

        public RenderFog(Camera camera, FogRenderer.FogMode mode, double partialTick, float nearPlaneDistance, float farPlaneDistance) {
            super(camera, partialTick);
            this.mode = mode;
            this.nearPlaneDistance = nearPlaneDistance;
            this.farPlaneDistance = farPlaneDistance;
        }

        public FogRenderer.FogMode getMode() {
            return mode;
        }

        public float getNearPlaneDistance() {
            return nearPlaneDistance;
        }

        public void setNearPlaneDistance(float nearPlaneDistance) {
            this.nearPlaneDistance = nearPlaneDistance;
        }

        public float getFarPlaneDistance() {
            return farPlaneDistance;
        }

        public void setFarPlaneDistance(float farPlaneDistance) {
            this.farPlaneDistance = farPlaneDistance;
        }
    }

    public static class ComputeFogColor extends ViewportEvent {
        private float red;
        private float green;
        private float blue;

        public ComputeFogColor(Camera camera, double partialTick, float red, float green, float blue) {
            super(camera, partialTick);
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public float getRed() {
            return red;
        }

        public void setRed(float red) {
            this.red = red;
        }

        public float getGreen() {
            return green;
        }

        public void setGreen(float green) {
            this.green = green;
        }

        public float getBlue() {
            return blue;
        }

        public void setBlue(float blue) {
            this.blue = blue;
        }
    }

    public static class ComputeFov extends ViewportEvent {
        private double fov;

        public ComputeFov(Camera camera, double partialTick, double fov) {
            super(camera, partialTick);
            this.fov = fov;
        }

        public double getFOV() {
            return fov;
        }

        public void setFOV(double fov) {
            this.fov = fov;
        }
    }
}

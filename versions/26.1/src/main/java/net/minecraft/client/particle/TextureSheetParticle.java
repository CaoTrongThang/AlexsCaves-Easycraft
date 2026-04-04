package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public abstract class TextureSheetParticle extends SingleQuadParticle {

    protected TextureSheetParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, (TextureAtlasSprite) null);
    }

    protected TextureSheetParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd, (TextureAtlasSprite) null);
    }

    public void pickSprite(SpriteSet spriteSet) {
        this.setSprite(spriteSet.get(this.random));
    }

    public int getLightColor(float partialTick) {
        return super.getLightCoords(partialTick);
    }

    @Override
    protected int getLightCoords(float partialTick) {
        return this.getLightColor(partialTick);
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }
}

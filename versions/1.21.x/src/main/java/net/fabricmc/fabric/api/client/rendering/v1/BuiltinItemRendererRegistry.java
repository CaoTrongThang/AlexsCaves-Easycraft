package net.fabricmc.fabric.api.client.rendering.v1;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BuiltinItemRendererRegistry {
    public static final BuiltinItemRendererRegistry INSTANCE = new BuiltinItemRendererRegistry();

    @FunctionalInterface
    public interface DynamicItemRenderer {
        void render(ItemStack stack, ItemDisplayContext displayContext, PoseStack matrices,
                MultiBufferSource vertexConsumers, int light, int overlay);
    }

    public void register(Item item, DynamicItemRenderer renderer) {
    }
}

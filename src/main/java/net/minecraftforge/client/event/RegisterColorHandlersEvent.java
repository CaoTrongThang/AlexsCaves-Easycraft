package net.minecraftforge.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;

public final class RegisterColorHandlersEvent {
    private RegisterColorHandlersEvent() {
    }

    public static class Item {
        public void register(ItemColor color, ItemLike... items) {
            ColorProviderRegistry.ITEM.register(color, items);
        }
    }

    public static class Block {
        public void register(BlockColor color, net.minecraft.world.level.block.Block... blocks) {
            ColorProviderRegistry.BLOCK.register(color, blocks);
        }
    }
}

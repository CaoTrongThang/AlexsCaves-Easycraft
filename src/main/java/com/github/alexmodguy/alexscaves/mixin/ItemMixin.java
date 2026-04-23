package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.forge_shim.common.IForgeItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements IForgeItem {
}

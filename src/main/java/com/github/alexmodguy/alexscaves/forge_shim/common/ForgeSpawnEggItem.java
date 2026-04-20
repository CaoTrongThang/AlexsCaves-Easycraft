package com.github.alexmodguy.alexscaves.forge_shim.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ForgeSpawnEggItem extends DeferredSpawnEggItem {
    public ForgeSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        super(type, primaryColor, secondaryColor, properties);
    }
}

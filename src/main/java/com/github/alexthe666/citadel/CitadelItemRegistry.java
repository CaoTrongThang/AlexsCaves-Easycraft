package com.github.alexthe666.citadel;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CitadelItemRegistry {
    public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ForgeRegistries.ITEMS, "citadel");

    public static final RegistryObject<Item> ICON_ITEM = DEF_REG.register("icon_item", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EFFECT_ITEM = DEF_REG.register("effect_item", () -> new Item(new Item.Properties()));

    private CitadelItemRegistry() {
    }
}

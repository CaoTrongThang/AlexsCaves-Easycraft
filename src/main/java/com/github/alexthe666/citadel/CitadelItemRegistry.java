package com.github.alexthe666.citadel;

import net.minecraft.world.item.Item;
import com.github.alexmodguy.alexscaves.forge_shim.registries.DeferredRegister;
import com.github.alexmodguy.alexscaves.forge_shim.registries.ForgeRegistries;
import com.github.alexmodguy.alexscaves.forge_shim.registries.RegistryObject;

public class CitadelItemRegistry {
    public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ForgeRegistries.ITEMS, "citadel");

    public static final RegistryObject<Item> ICON_ITEM = DEF_REG.register("icon_item", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EFFECT_ITEM = DEF_REG.register("effect_item", () -> new Item(new Item.Properties()));

    private CitadelItemRegistry() {
    }
}

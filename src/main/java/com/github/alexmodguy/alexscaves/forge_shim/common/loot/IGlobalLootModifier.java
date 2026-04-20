package com.github.alexmodguy.alexscaves.forge_shim.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public interface IGlobalLootModifier {
    Codec<LootItemCondition[]> LOOT_CONDITIONS_CODEC = Codec.unit(new LootItemCondition[0]);

    ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context);

    MapCodec<? extends IGlobalLootModifier> codec();
}

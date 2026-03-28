package com.github.alexmodguy.alexscaves.server.misc;

import com.github.alexmodguy.alexscaves.server.block.RadrockUraniumOreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public final class ACBlockCompat {

    private ACBlockCompat() {
    }

    public static void awardExperience(ServerLevel level, BlockPos pos, int amount) {
        if (amount > 0) {
            ExperienceOrb.award(level, Vec3.atCenterOf(pos), amount);
        }
    }

    public static int getExpDrop(BlockState state, Level level, BlockPos pos, Entity breaker, ItemStack tool) {
        if (state.getBlock() instanceof RadrockUraniumOreBlock oreBlock) {
            return oreBlock.getExpDrop(state, level, level.random, pos, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool), EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool));
        }
        return 0;
    }
}

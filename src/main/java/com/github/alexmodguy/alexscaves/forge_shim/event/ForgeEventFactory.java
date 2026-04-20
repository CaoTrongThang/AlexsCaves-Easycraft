package com.github.alexmodguy.alexscaves.forge_shim.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.item.ItemStack;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;

public final class ForgeEventFactory {
    private ForgeEventFactory() {
    }

    public static BlockGrowFeatureEvent blockGrowFeature(ServerLevel level, RandomSource random, BlockPos pos, Holder<ConfiguredFeature<?, ?>> feature) {
        return new BlockGrowFeatureEvent(feature);
    }

    public static boolean getMobGriefingEvent(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    public static BlockState fireFluidPlaceBlockEvent(Level level, BlockPos pos, BlockPos fromPos, BlockState newState) {
        return newState;
    }

    public static boolean onProjectileImpact(Entity projectile, HitResult hitResult) {
        return false;
    }

    public static void onLivingConvert(LivingEntity from, LivingEntity to) {
    }

    public static boolean onEntityDestroyBlock(Entity entity, BlockPos pos, BlockState state) {
        return true;
    }

    public static boolean checkSpawnPosition(Mob mob, ServerLevelAccessor level, MobSpawnType spawnType) {
        return true;
    }

    public static void onPlayerDestroyItem(Player player, ItemStack stack, InteractionHand hand) {
    }

    public static float getBreakSpeed(Player player, BlockState state, float speed, BlockPos pos) {
        return speed;
    }

    public static int onApplyBonemeal(Player player, Level level, BlockPos pos, BlockState state, ItemStack stack) {
        return 0;
    }

    public static class BlockGrowFeatureEvent extends Event {
        private final Holder<ConfiguredFeature<?, ?>> feature;

        public BlockGrowFeatureEvent(Holder<ConfiguredFeature<?, ?>> feature) {
            this.feature = feature;
        }

        public Holder<ConfiguredFeature<?, ?>> getFeature() {
            return feature;
        }
    }
}

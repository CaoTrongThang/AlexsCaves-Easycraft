package net.neoforged.neoforge.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public final class EventHooks {
    private EventHooks() {
    }

    public static boolean canEntityGrief(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    public static boolean onEntityDestroyBlock(Entity entity, BlockPos pos, BlockState state) {
        return !state.isAir() && canEntityGrief(entity.level(), entity);
    }

    public static void onLivingConvert(Mob from, Mob to) {
    }

    public static BlockState fireFluidPlaceBlockEvent(Level level, BlockPos pos, BlockPos fromPos, BlockState state) {
        return state;
    }

    public static boolean onProjectileImpact(Entity projectile, HitResult hitResult) {
        return false;
    }

    public static boolean checkSpawnPosition(Mob mob, ServerLevelAccessor level, MobSpawnType spawnType) {
        return mob.checkSpawnRules(level, spawnType) && mob.checkSpawnObstruction(level);
    }

    public static void firePlayerSmeltedEvent(Player player, ItemStack stack) {
    }
}

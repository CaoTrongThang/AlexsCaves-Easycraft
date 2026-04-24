package com.github.alexmodguy.alexscaves.server.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KaijuSwimmingNavigation extends WaterBoundPathNavigation {

    public KaijuSwimmingNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new SwimNodeEvaluator(true);
        return new PatchedPathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected void followThePath() {
        if (this.path == null) {
            return;
        }

        Vec3 entityPos = this.getTempMobPos();
        int pathLength = this.path.getNodeCount();

        // Shortcutting logic: try to skip unnecessary nodes if there's a clear path
        for (int i = pathLength - 1; i > this.path.getNextNodeIndex(); i--) {
            Vec3 nodePos = this.path.getEntityPosAtNode(this.mob, i);
            if (nodePos.distanceToSqr(entityPos) < 1024.0D && this.canMoveDirectly(entityPos, nodePos)) {
                this.path.setNextNodeIndex(i);
                break;
            }
        }

        super.followThePath();
    }

    @Override
    protected boolean canMoveDirectly(Vec3 start, Vec3 end) {
        // Simple AABB-like sweep check in water
        float width = this.mob.getBbWidth() * 0.8F;
        Vec3 offset = end.subtract(start).normalize().cross(new Vec3(0, 1, 0)).scale(width * 0.5F);

        // Check center, left, and right to give a broad enough "tunnel" for the big
        // fish
        return isLineClear(start, end) && isLineClear(start.add(offset), end.add(offset))
                && isLineClear(start.subtract(offset), end.subtract(offset));
    }

    private boolean isLineClear(Vec3 start, Vec3 end) {
        return this.level
                .clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob))
                .getType() == HitResult.Type.MISS;
    }

    static class PatchedPathFinder extends PathFinder {
        public PatchedPathFinder(net.minecraft.world.level.pathfinder.NodeEvaluator processor, int maxVisitedNodes) {
            super(processor, maxVisitedNodes);
        }

        @Override
        public Path findPath(net.minecraft.world.level.PathNavigationRegion region, Mob mob,
                Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
            Path path = super.findPath(region, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
            return path == null ? null : new PatchedPath(path);
        }
    }

    static class PatchedPath extends Path {
        public PatchedPath(Path original) {
            super(copyPathPoints(original), original.getTarget(), original.canReach());
        }

        @Override
        public Vec3 getEntityPosAtNode(Entity entity, int index) {
            Node node = this.getNode(index);
            // Center the large entity on the node by adding half-width offset
            // We use (width + 1) * 0.5 to ensure it fits well in its footprint
            double offset = Mth.floor(entity.getBbWidth() + 1.0F) * 0.5D;
            return new Vec3(node.x + offset, node.y + (double) entity.getBbHeight() * 0.5D, node.z + offset);
        }

        private static List<Node> copyPathPoints(Path original) {
            List<Node> points = new ArrayList<>();
            for (int i = 0; i < original.getNodeCount(); i++) {
                points.add(original.getNode(i));
            }
            return points;
        }
    }
}

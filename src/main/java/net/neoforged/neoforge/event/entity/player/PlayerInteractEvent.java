package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PlayerInteractEvent extends PlayerEvent {
    private final Level level;
    private final InteractionHand hand;
    private final ItemStack itemStack;
    private boolean canceled;
    private InteractionResult cancellationResult = InteractionResult.PASS;

    public PlayerInteractEvent(Player entity, Level level, InteractionHand hand, ItemStack itemStack) {
        super(entity);
        this.level = level;
        this.hand = hand;
        this.itemStack = itemStack;
    }

    public Level getLevel() {
        return level;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public InteractionResult getCancellationResult() {
        return cancellationResult;
    }

    public void setCancellationResult(InteractionResult cancellationResult) {
        this.cancellationResult = cancellationResult;
    }

    public static class EntityInteract extends PlayerInteractEvent {
        private final Entity target;

        public EntityInteract(Player entity, Level level, InteractionHand hand, ItemStack itemStack, Entity target) {
            super(entity, level, hand, itemStack);
            this.target = target;
        }

        public Entity getTarget() {
            return target;
        }
    }

    public static class RightClickItem extends PlayerInteractEvent {
        public RightClickItem(Player entity, Level level, InteractionHand hand, ItemStack itemStack) {
            super(entity, level, hand, itemStack);
        }
    }
}

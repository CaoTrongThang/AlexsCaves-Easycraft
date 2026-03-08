package net.neoforged.neoforge.event;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public class AnvilUpdateEvent extends Event {
    private final ItemStack left;
    private final ItemStack right;
    private int cost;
    private ItemStack output = ItemStack.EMPTY;

    public AnvilUpdateEvent(ItemStack left, ItemStack right) {
        this.left = left;
        this.right = right;
    }

    public ItemStack getLeft() {
        return left;
    }

    public ItemStack getRight() {
        return right;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ItemStack getOutput() {
        return output;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }
}

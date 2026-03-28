package net.neoforged.neoforge.common.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BrewingRecipe {
    private final Ingredient input;
    private final Ingredient ingredient;
    private final ItemStack output;

    public BrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    public boolean isInput(ItemStack stack) {
        return input.test(stack);
    }

    public boolean isIngredient(ItemStack stack) {
        return ingredient.test(stack);
    }

    public ItemStack getOutput() {
        return output.copy();
    }
}

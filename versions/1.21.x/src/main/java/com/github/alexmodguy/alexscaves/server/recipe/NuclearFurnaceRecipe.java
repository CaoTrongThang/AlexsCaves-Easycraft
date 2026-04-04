package com.github.alexmodguy.alexscaves.server.recipe;

import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeType;

public class NuclearFurnaceRecipe extends AbstractCookingRecipe {
    private final CookingBookCategory category;

    public NuclearFurnaceRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
        this.category = category;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(this.furnaceIcon());
    }

    @Override
    public RecipeSerializer<? extends AbstractCookingRecipe> getSerializer() {
        return (RecipeSerializer<? extends AbstractCookingRecipe>) ACRecipeRegistry.NUCLEAR_FURNACE.get();
    }

    @Override
    public RecipeType<? extends AbstractCookingRecipe> getType() {
        return ACRecipeRegistry.NUCLEAR_FURNACE_TYPE.get();
    }

    @Override
    protected Item furnaceIcon() {
        return ACBlockRegistry.NUCLEAR_FURNACE.get().asItem();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category) {
            case BLOCKS -> RecipeBookCategories.FURNACE_BLOCKS;
            case FOOD -> RecipeBookCategories.FURNACE_FOOD;
            case MISC -> RecipeBookCategories.FURNACE_MISC;
        };
    }
}

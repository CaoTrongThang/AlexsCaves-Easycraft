package com.github.alexmodguy.alexscaves.forge_shim.common.brewing;

import java.util.ArrayList;
import java.util.List;

public final class BrewingRecipeRegistry {
    private static final List<Object> RECIPES = new ArrayList<>();

    private BrewingRecipeRegistry() {
    }

    public static void addRecipe(Object recipe) {
        RECIPES.add(recipe);
    }

    public static List<Object> getRecipes() {
        return RECIPES;
    }
}

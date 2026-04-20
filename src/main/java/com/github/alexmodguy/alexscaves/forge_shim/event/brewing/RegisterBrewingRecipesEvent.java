package com.github.alexmodguy.alexscaves.forge_shim.event.brewing;

import net.minecraft.world.level.ItemLike;
import com.github.alexmodguy.alexscaves.forge_shim.eventbus.api.Event;
import com.github.alexmodguy.alexscaves.forge_shim.common.brewing.BrewingRecipe;

import java.util.ArrayList;
import java.util.List;

public class RegisterBrewingRecipesEvent extends Event {
    private final Builder builder = new Builder();

    public Builder getBuilder() {
        return builder;
    }

    public static class Builder {
        private final List<BrewingRecipe> recipes = new ArrayList<>();

        public void addRecipe(BrewingRecipe recipe) {
            recipes.add(recipe);
        }

        public void addMix(Object inputPotion, ItemLike ingredient, Object outputPotion) {
        }

        public List<BrewingRecipe> getRecipes() {
            return recipes;
        }
    }
}

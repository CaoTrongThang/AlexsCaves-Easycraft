package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.client.gui.book.widget.CraftingRecipeWidget;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CraftingRecipeWidget.class)
public class CraftingRecipeWidgetMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/Ingredient;isEmpty()Z"))
    private boolean alexscaves$redirectIngredientIsEmpty(Ingredient instance) {
        return instance.isEmpty() || instance.getItems().length == 0;
    }
}

package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.item.PrimordialArmorItem;
import com.github.alexmodguy.alexscaves.server.misc.ACTagRegistry;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    @Shadow
    public abstract void eat(int nutrition, float saturation);

    @Inject(
            method = {"Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;)V"},
            cancellable = true,
            at = @At(value = "HEAD")
    )
    public void ac_eat(Item item, ItemStack stack, CallbackInfo ci) {
        if (stack.is(ACTagRegistry.RAW_MEATS)) {
            FoodProperties foodProperties = item.getFoodProperties();
            if (foodProperties != null) {
                ci.cancel();
                this.eat(foodProperties.getNutrition(), foodProperties.getSaturationModifier());
            }
        }
    }

}

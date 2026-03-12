package com.github.alexmodguy.alexscaves.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    @Shadow
    private DataSlot cost;

    protected AnvilMenuMixin() {
        super(null, 0, null, null);
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void ac_applyCustomAnvilUpdate(CallbackInfo ci) {
        AnvilUpdateEvent event = new AnvilUpdateEvent(this.inputSlots.getItem(0), this.inputSlots.getItem(1));
        NeoForge.EVENT_BUS.post(event);
        if (event.getOutput().isEmpty()) {
            return;
        }

        this.cost.set(event.getCost());
        this.resultSlots.setItem(0, event.getOutput());
        this.broadcastChanges();
        ci.cancel();
    }
}

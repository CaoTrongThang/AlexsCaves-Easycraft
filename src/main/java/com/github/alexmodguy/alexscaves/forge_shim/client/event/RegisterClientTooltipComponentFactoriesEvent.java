package com.github.alexmodguy.alexscaves.forge_shim.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.function.Function;

public class RegisterClientTooltipComponentFactoriesEvent {
    public <T> void register(Class<T> tooltipClass, Function<T, ClientTooltipComponent> factory) {
        TooltipComponentCallback.EVENT.register(data -> tooltipClass.isInstance(data) ? factory.apply(tooltipClass.cast(data)) : null);
    }
}

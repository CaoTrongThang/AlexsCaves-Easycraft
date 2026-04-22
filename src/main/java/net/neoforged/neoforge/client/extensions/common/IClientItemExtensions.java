package net.neoforged.neoforge.client.extensions.common;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IClientItemExtensions {

    default BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return null;
    }

    default HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultModel) {
        return defaultModel;
    }
}

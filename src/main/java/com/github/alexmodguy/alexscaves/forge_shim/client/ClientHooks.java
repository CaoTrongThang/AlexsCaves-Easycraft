package com.github.alexmodguy.alexscaves.forge_shim.client;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.item.DarknessArmorItem;
import com.github.alexmodguy.alexscaves.server.item.DivingArmorItem;
import com.github.alexmodguy.alexscaves.server.item.GingerbreadArmorItem;
import com.github.alexmodguy.alexscaves.server.item.HazmatArmorItem;
import com.github.alexmodguy.alexscaves.server.item.PrimordialArmorItem;
import com.github.alexmodguy.alexscaves.server.item.RainbounceBootsItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import com.github.alexmodguy.alexscaves.forge_shim.client.extensions.common.IClientItemExtensions;

public final class ClientHooks {
    private ClientHooks() {
    }

    public static BakedModel handleCameraTransforms(PoseStack poseStack, BakedModel bakedModel, ItemDisplayContext context, boolean leftHand) {
        return bakedModel;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Model getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
        Object properties = AlexsCaves.PROXY.getArmorProperties();
        if (properties instanceof IClientItemExtensions extensions) {
            return extensions.getHumanoidArmorModel(entity, stack, slot, defaultModel);
        }
        return defaultModel;
    }

    public static String getArmorTexture(LivingEntity entity, ItemStack stack, String defaultTexture, EquipmentSlot slot, String type) {
        if (stack.getItem() instanceof DarknessArmorItem item) {
            return item.getArmorTexture(stack, entity, slot, type);
        }
        if (stack.getItem() instanceof DivingArmorItem item) {
            return item.getArmorTexture(stack, entity, slot, type);
        }
        if (stack.getItem() instanceof GingerbreadArmorItem item) {
            return item.getArmorTexture(stack, entity, slot, type);
        }
        if (stack.getItem() instanceof HazmatArmorItem item) {
            return item.getArmorTexture(stack, entity, slot, type);
        }
        if (stack.getItem() instanceof PrimordialArmorItem item) {
            return item.getArmorTexture(stack, entity, slot, type);
        }
        if (stack.getItem() instanceof RainbounceBootsItem item) {
            return item.getArmorTexture(stack, entity, slot, type);
        }
        return defaultTexture;
    }
}

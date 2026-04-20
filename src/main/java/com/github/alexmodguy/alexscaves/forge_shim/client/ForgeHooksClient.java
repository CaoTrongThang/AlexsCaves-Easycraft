package com.github.alexmodguy.alexscaves.forge_shim.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class ForgeHooksClient {
    private ForgeHooksClient() {
    }

    public static Model getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
        return ClientHooks.getArmorModel(entity, stack, slot, (HumanoidModel) defaultModel);
    }

    public static String getArmorTexture(LivingEntity entity, ItemStack stack, String defaultTexture, EquipmentSlot slot, String type) {
        return ClientHooks.getArmorTexture(entity, stack, defaultTexture, slot, type);
    }

    public static BakedModel handleCameraTransforms(PoseStack poseStack, BakedModel model, ItemDisplayContext context, boolean leftHand) {
        return model;
    }
}

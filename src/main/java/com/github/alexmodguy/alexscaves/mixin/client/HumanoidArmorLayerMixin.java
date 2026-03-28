package com.github.alexmodguy.alexscaves.mixin.client;


import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.render.item.ACArmorRenderProperties;
import com.github.alexmodguy.alexscaves.server.item.DarknessArmorItem;
import com.github.alexmodguy.alexscaves.server.item.DivingArmorItem;
import com.github.alexmodguy.alexscaves.server.item.GingerbreadArmorItem;
import com.github.alexmodguy.alexscaves.server.item.HazmatArmorItem;
import com.github.alexmodguy.alexscaves.server.item.PrimordialArmorItem;
import com.github.alexmodguy.alexscaves.server.item.RainbounceBootsItem;
import com.github.alexmodguy.alexscaves.server.item.CustomArmorPostRender;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin extends RenderLayer {

    private static final Map<String, ResourceLocation> AC_ARMOR_LOCATION_CACHE = Maps.newHashMap();
    private ItemStack lastArmorItemStackRendered = ItemStack.EMPTY;

    @Shadow
    protected abstract void setPartVisibility(HumanoidModel humanoidModel, EquipmentSlot equipmentSlot);

    public HumanoidArmorLayerMixin(RenderLayerParent renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(
            method = {"Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V"},
            at = @At(value = "HEAD"),
            remap = true,
            cancellable = true
    )
    private void ac_renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int light, HumanoidModel humanoidModel, CallbackInfo ci) {
        ItemStack itemstack = livingEntity.getItemBySlot(equipmentSlot);
        Item item = itemstack.getItem();
        if (!(item instanceof ArmorItem armorItem) || !isAlexsCavesArmor(item) || armorItem.getEquipmentSlot() != equipmentSlot) {
            return;
        }

        ci.cancel();
        lastArmorItemStackRendered = itemstack;
        boolean legs = equipmentSlot == EquipmentSlot.LEGS;
        HumanoidModel parentModel = this.getParentModel() instanceof HumanoidModel humanoidModel1 ? humanoidModel1 : humanoidModel;
        Model armorModel = ForgeHooksClient.getArmorModel(livingEntity, itemstack, equipmentSlot, parentModel);
        HumanoidModel humanoidArmorModel = armorModel instanceof HumanoidModel armorHumanoidModel ? armorHumanoidModel : humanoidModel;
        parentModel.copyPropertiesTo(humanoidArmorModel);
        setPartVisibility(humanoidArmorModel, equipmentSlot);
        ResourceLocation texture = getACArmorResource(livingEntity, itemstack, equipmentSlot, null);

        if (item instanceof CustomArmorPostRender) {
            ACArmorRenderProperties.renderCustomArmor(poseStack, multiBufferSource, light, lastArmorItemStackRendered, armorItem, humanoidArmorModel, legs, texture);
        } else {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(texture), false, itemstack.hasFoil());
            humanoidArmorModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }


    /* copy of forge method */
    private ResourceLocation getACArmorResource(LivingEntity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = isAlexsCavesArmor(item) ? AlexsCaves.MODID : "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (slot == EquipmentSlot.LEGS ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));

        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = AC_ARMOR_LOCATION_CACHE.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            AC_ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
        }

        return resourcelocation;
    }

    private static boolean isAlexsCavesArmor(Item item) {
        return item instanceof PrimordialArmorItem
            || item instanceof HazmatArmorItem
            || item instanceof DivingArmorItem
            || item instanceof DarknessArmorItem
            || item instanceof RainbounceBootsItem
            || item instanceof GingerbreadArmorItem;
    }
}

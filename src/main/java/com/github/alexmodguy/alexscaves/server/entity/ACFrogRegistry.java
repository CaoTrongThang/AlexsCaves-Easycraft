package com.github.alexmodguy.alexscaves.server.entity;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.FrogVariant;
import com.github.alexmodguy.alexscaves.forge_shim.registries.DeferredRegister;
import com.github.alexmodguy.alexscaves.forge_shim.registries.RegistryObject;

public class ACFrogRegistry {

    public static final DeferredRegister<FrogVariant> DEF_REG = DeferredRegister.create(Registries.FROG_VARIANT, AlexsCaves.MODID);

    public static final RegistryObject<FrogVariant> PRIMORDIAL = DEF_REG.register("primordial", () -> new FrogVariant(new ResourceLocation(AlexsCaves.MODID, "textures/entity/primordial_frog.png")));

}

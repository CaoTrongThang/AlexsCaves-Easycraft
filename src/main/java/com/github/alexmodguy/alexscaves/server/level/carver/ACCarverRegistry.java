package com.github.alexmodguy.alexscaves.server.level.carver;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import com.github.alexmodguy.alexscaves.forge_shim.registries.DeferredRegister;
import com.github.alexmodguy.alexscaves.forge_shim.registries.ForgeRegistries;
import com.github.alexmodguy.alexscaves.forge_shim.registries.RegistryObject;

public class ACCarverRegistry {

    public static final DeferredRegister<WorldCarver<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, AlexsCaves.MODID);

    //Unused for now.
    public static RegistryObject<WaterBubbleCarver> WATER_BUBBLE_CARVER = DEF_REG.register("water_bubble_carver", () -> new WaterBubbleCarver(CaveCarverConfiguration.CODEC));

}

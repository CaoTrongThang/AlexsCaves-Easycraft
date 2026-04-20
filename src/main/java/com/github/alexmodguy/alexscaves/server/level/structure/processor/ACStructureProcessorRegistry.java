package com.github.alexmodguy.alexscaves.server.level.structure.processor;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import com.github.alexmodguy.alexscaves.forge_shim.registries.DeferredRegister;
import com.github.alexmodguy.alexscaves.forge_shim.registries.RegistryObject;

public class ACStructureProcessorRegistry {

    public static final DeferredRegister<StructureProcessorType<?>> DEF_REG = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, AlexsCaves.MODID);

    public static final RegistryObject<StructureProcessorType<UndergroundCabinProcessor>> UNDERGROUND_CABIN = DEF_REG.register("underground_cabin", () -> () -> UndergroundCabinProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<WhalefallProcessor>> WHALEFALL = DEF_REG.register("whalefall", () -> () -> WhalefallProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<WhalefallProcessor>> WHALEFALL_SKULL = DEF_REG.register("whalefall_skull", () -> () -> WhalefallProcessor.CODEC_SKULL);
    public static final RegistryObject<StructureProcessorType<LollipopProcessor>> LOLLIPOP = DEF_REG.register("lollipop", () -> () -> LollipopProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<SodaBottleProcessor>> SODA_BOTTLE = DEF_REG.register("soda_bottle", () -> () -> SodaBottleProcessor.CODEC);

}

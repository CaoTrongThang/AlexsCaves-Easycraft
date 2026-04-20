package com.github.alexmodguy.alexscaves.fabric;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import net.fabricmc.api.ModInitializer;

public class AlexsCavesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ACMultipartFabric.registerCommon();
        ACFabricEventBridge.registerCommon();
        FabricRegistryBootstrap.bootstrapCommon();
        AlexsCaves.setProxy(new com.github.alexmodguy.alexscaves.server.CommonProxy());
        AlexsCaves.init();
        com.github.alexmodguy.alexscaves.forge_shim.common.MinecraftForge.EVENT_BUS.register(new CommonEvents());
    }
}

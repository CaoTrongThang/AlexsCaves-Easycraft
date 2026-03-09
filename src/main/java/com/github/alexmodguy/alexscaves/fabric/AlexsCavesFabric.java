package com.github.alexmodguy.alexscaves.fabric;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.CommonProxy;
import com.github.alexmodguy.alexscaves.server.event.CommonEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class AlexsCavesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricRegistryBootstrap.bootstrapCommon();
        AlexsCaves.setProxy(new com.github.alexmodguy.alexscaves.server.CommonProxy());
        AlexsCaves.init();
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(new CommonEvents());
        ServerLifecycleEvents.SERVER_STARTING.register(CommonEvents::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPED.register(CommonEvents::onServerStopped);
    }
}

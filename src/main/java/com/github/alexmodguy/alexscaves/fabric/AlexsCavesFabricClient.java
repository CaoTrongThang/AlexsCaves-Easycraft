package com.github.alexmodguy.alexscaves.fabric;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.ClientProxy;
import net.fabricmc.api.ClientModInitializer;

public class AlexsCavesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ACNetworkingFabricClient.registerClient();
        ACMultipartFabricClient.registerClient();
        ACFabricClientEventBridge.registerClient();
        FabricRegistryBootstrap.bootstrapClient();
        AlexsCaves.setProxy(new ClientProxy());
        AlexsCaves.initClient();
    }
}

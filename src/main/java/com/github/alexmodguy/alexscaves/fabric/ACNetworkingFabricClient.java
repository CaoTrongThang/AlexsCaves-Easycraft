package com.github.alexmodguy.alexscaves.fabric;

public final class ACNetworkingFabricClient {
    private static boolean initialized;

    private ACNetworkingFabricClient() {
    }

    public static void registerClient() {
        if (initialized) {
            return;
        }
        initialized = true;
    }
}

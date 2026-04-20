package com.github.alexmodguy.alexscaves.forge_shim.api.distmarker;

public enum Dist {
    CLIENT,
    DEDICATED_SERVER;

    public boolean isClient() {
        return this == CLIENT;
    }
}

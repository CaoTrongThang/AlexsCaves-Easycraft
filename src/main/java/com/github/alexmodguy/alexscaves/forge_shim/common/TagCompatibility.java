package com.github.alexmodguy.alexscaves.forge_shim.common;

import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class TagCompatibility {

    private static final Map<ResourceLocation, ResourceLocation> FORGE_TO_C_LINKS = new HashMap<>();

    static {
        // Dyes: forge:dyes/yellow -> c:yellow_dyes
        String[] colors = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray",
                "cyan", "purple", "blue", "brown", "green", "red", "black" };
        for (String color : colors) {
            FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "dyes/" + color),
                    new ResourceLocation("c", color + "_dyes"));
        }

        // Common Materials
        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "ingots/iron"), new ResourceLocation("c", "iron_ingots"));
        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "ingots/gold"), new ResourceLocation("c", "gold_ingots"));
        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "ingots/netherite"),
                new ResourceLocation("c", "netherite_ingots"));
        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "ingots/uranium"),
                new ResourceLocation("c", "uranium_ingots"));

        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "storage_blocks/iron"),
                new ResourceLocation("c", "iron_blocks"));
        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "storage_blocks/gold"),
                new ResourceLocation("c", "gold_blocks"));
        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "storage_blocks/diamond"),
                new ResourceLocation("c", "diamond_blocks"));
        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "storage_blocks/uranium"),
                new ResourceLocation("c", "uranium_blocks"));

        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "glass/colorless"),
                new ResourceLocation("c", "glass_blocks"));
        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "glass/all"), new ResourceLocation("c", "glass_blocks"));

        FORGE_TO_C_LINKS.put(new ResourceLocation("forge", "concrete"), new ResourceLocation("c", "concrete"));
    }

    public static Map<ResourceLocation, ResourceLocation> getLinks() {
        return FORGE_TO_C_LINKS;
    }
}

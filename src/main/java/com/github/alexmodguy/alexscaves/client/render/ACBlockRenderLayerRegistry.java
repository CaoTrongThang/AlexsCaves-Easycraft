package com.github.alexmodguy.alexscaves.client.render;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Forge honored model render_type hints for mod blocks.
 * Fabric does not, so mirror those hints onto BlockRenderLayerMap.
 */
public final class ACBlockRenderLayerRegistry {
    private static final Map<ResourceLocation, String> MODEL_RENDER_TYPE_CACHE = new HashMap<>();
    private static final Map<ResourceLocation, String> VANILLA_PARENT_RENDER_TYPES = Map.of(
        new ResourceLocation("minecraft", "block/leaves"), "cutout_mipped",
        new ResourceLocation("minecraft", "block/cross"), "cutout",
        new ResourceLocation("minecraft", "block/tinted_cross"), "cutout",
        new ResourceLocation("minecraft", "block/flower_pot_cross"), "cutout",
        new ResourceLocation("minecraft", "block/crop"), "cutout"
    );

    private ACBlockRenderLayerRegistry() {
    }

    public static void register() {
        int cutoutBlocks = 0;
        int cutoutMippedBlocks = 0;
        int translucentBlocks = 0;
        ClassLoader classLoader = ACBlockRenderLayerRegistry.class.getClassLoader();

        for (ResourceLocation blockId : BuiltInRegistries.BLOCK.keySet()) {
            if (!AlexsCaves.MODID.equals(blockId.getNamespace())) {
                continue;
            }

            String renderTypeName = inferBlockRenderType(classLoader, blockId);
            RenderType renderType = toRenderType(renderTypeName);
            if (renderType == null) {
                continue;
            }

            Block block = BuiltInRegistries.BLOCK.get(blockId);
            BlockRenderLayerMap.INSTANCE.putBlock(block, renderType);
            if ("cutout".equals(renderTypeName)) {
                cutoutBlocks++;
            } else if ("cutout_mipped".equals(renderTypeName)) {
                cutoutMippedBlocks++;
            } else if ("translucent".equals(renderTypeName)) {
                translucentBlocks++;
            }
        }

        AlexsCaves.LOGGER.info(
            "Registered {} cutout, {} cutout_mipped, and {} translucent Alex's Caves blocks from asset render metadata",
            cutoutBlocks,
            cutoutMippedBlocks,
            translucentBlocks
        );
    }

    @Nullable
    private static String inferBlockRenderType(ClassLoader classLoader, ResourceLocation blockId) {
        JsonObject blockstateJson = readJsonObject(
            classLoader,
            "assets/" + blockId.getNamespace() + "/blockstates/" + blockId.getPath() + ".json"
        );
        if (blockstateJson == null) {
            return null;
        }

        String renderType = null;
        JsonObject variants = getObject(blockstateJson, "variants");
        if (variants != null) {
            for (Map.Entry<String, JsonElement> variantEntry : variants.entrySet()) {
                renderType = mergeRenderType(renderType, resolveEntryRenderType(classLoader, variantEntry.getValue()));
            }
        }

        JsonElement multipartElement = blockstateJson.get("multipart");
        if (multipartElement != null && multipartElement.isJsonArray()) {
            for (JsonElement partElement : multipartElement.getAsJsonArray()) {
                JsonObject partObject = partElement.getAsJsonObject();
                renderType = mergeRenderType(renderType, resolveEntryRenderType(classLoader, partObject.get("apply")));
            }
        }
        return renderType;
    }

    @Nullable
    private static String resolveEntryRenderType(ClassLoader classLoader, @Nullable JsonElement entryElement) {
        if (entryElement == null) {
            return null;
        }
        if (entryElement.isJsonArray()) {
            String renderType = null;
            for (JsonElement childElement : entryElement.getAsJsonArray()) {
                renderType = mergeRenderType(renderType, resolveEntryRenderType(classLoader, childElement));
            }
            return renderType;
        }
        if (!entryElement.isJsonObject()) {
            return null;
        }
        JsonObject entryObject = entryElement.getAsJsonObject();
        JsonElement modelElement = entryObject.get("model");
        if (modelElement == null || !modelElement.isJsonPrimitive()) {
            return null;
        }
        ResourceLocation modelId = normalizeModelId(modelElement.getAsString());
        if (modelId == null) {
            return null;
        }
        return resolveModelRenderType(classLoader, modelId, new HashSet<ResourceLocation>());
    }

    @Nullable
    private static String resolveModelRenderType(ClassLoader classLoader, ResourceLocation modelId, Set<ResourceLocation> visited) {
        String cached = MODEL_RENDER_TYPE_CACHE.get(modelId);
        if (cached != null) {
            return cached;
        }
        String vanillaParentRenderType = VANILLA_PARENT_RENDER_TYPES.get(modelId);
        if (vanillaParentRenderType != null) {
            MODEL_RENDER_TYPE_CACHE.put(modelId, vanillaParentRenderType);
            return vanillaParentRenderType;
        }
        if (!visited.add(modelId)) {
            return null;
        }

        JsonObject modelJson = readJsonObject(
            classLoader,
            "assets/" + modelId.getNamespace() + "/models/" + modelId.getPath() + ".json"
        );
        if (modelJson == null) {
            return null;
        }

        JsonElement renderTypeElement = modelJson.get("render_type");
        if (renderTypeElement != null && renderTypeElement.isJsonPrimitive()) {
            String renderTypeName = renderTypeElement.getAsString();
            MODEL_RENDER_TYPE_CACHE.put(modelId, renderTypeName);
            return renderTypeName;
        }

        JsonElement parentElement = modelJson.get("parent");
        if (parentElement != null && parentElement.isJsonPrimitive()) {
            ResourceLocation parentId = normalizeModelId(parentElement.getAsString());
            if (parentId == null) {
                return null;
            }
            String inheritedRenderType = resolveModelRenderType(classLoader, parentId, visited);
            if (inheritedRenderType != null) {
                MODEL_RENDER_TYPE_CACHE.put(modelId, inheritedRenderType);
            }
            return inheritedRenderType;
        }
        return null;
    }

    @Nullable
    private static JsonObject readJsonObject(ClassLoader classLoader, String resourcePath) {
        try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                return null;
            }
            String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).trim();
            if (json.endsWith(",")) {
                json = json.substring(0, json.length() - 1);
            }
            try (Reader reader = new StringReader(json)) {
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(true);
                JsonElement jsonElement = JsonParser.parseReader(jsonReader);
                return jsonElement != null && jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;
            }
        } catch (Exception exception) {
            return null;
        }
    }

    @Nullable
    private static JsonObject getObject(JsonObject parent, String key) {
        JsonElement element = parent.get(key);
        return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    @Nullable
    private static ResourceLocation normalizeModelId(String modelId) {
        try {
            ResourceLocation resourceLocation = modelId.contains(":")
                ? new ResourceLocation(modelId)
                : new ResourceLocation(AlexsCaves.MODID, modelId);
            String path = resourceLocation.getPath();
            if (!path.startsWith("block/") && !path.startsWith("item/")) {
                path = "block/" + path;
            }
            return new ResourceLocation(resourceLocation.getNamespace(), path);
        } catch (Exception exception) {
            return null;
        }
    }

    @Nullable
    private static String mergeRenderType(@Nullable String current, @Nullable String candidate) {
        if (candidate == null) {
            return current;
        }
        if (current == null || renderTypePriority(candidate) > renderTypePriority(current)) {
            return candidate;
        }
        return current;
    }

    private static int renderTypePriority(String renderTypeName) {
        if ("translucent".equals(renderTypeName)) {
            return 3;
        }
        if ("cutout_mipped".equals(renderTypeName)) {
            return 2;
        }
        if ("cutout".equals(renderTypeName)) {
            return 1;
        }
        return 0;
    }

    @Nullable
    private static RenderType toRenderType(@Nullable String renderTypeName) {
        if (renderTypeName == null) {
            return null;
        }
        if ("cutout".equals(renderTypeName)) {
            return RenderType.cutout();
        }
        if ("cutout_mipped".equals(renderTypeName)) {
            return RenderType.cutoutMipped();
        }
        if ("translucent".equals(renderTypeName)) {
            return RenderType.translucent();
        }
        return null;
    }
}

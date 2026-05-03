package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.forge_shim.common.TagCompatibility;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(TagLoader.class)
public class TagLoaderMixin {

    /**
     * Safely injects Forge-to-Fabric tag links during the tag loading process.
     * Instead of redirecting the TagKey identity (which causes collisions),
     * we add a TagEntry that makes the forge:X tag include the c:X tag.
     */
    @Inject(method = "load", at = @At("RETURN"))
    private void alexscaves$injectForgeTagLinks(ResourceManager resourceManager,
            CallbackInfoReturnable<Map<ResourceLocation, List<TagLoader.EntryWithSource>>> cir) {
        Map<ResourceLocation, List<TagLoader.EntryWithSource>> map = cir.getReturnValue();

        for (Map.Entry<ResourceLocation, ResourceLocation> link : TagCompatibility.getLinks().entrySet()) {
            ResourceLocation forgeRL = link.getKey();
            ResourceLocation cRL = link.getValue();

            // We only inject if c:X exists and forge:X is missing or doesn't already link
            // to c:X
            if (map.containsKey(cRL)) {
                List<TagLoader.EntryWithSource> entries = map.computeIfAbsent(forgeRL, k -> new ArrayList<>());

                // Create an optional TagEntry that references the 'c' tag.
                // Using optionalTag() ensures that if the 'c' tag is missing or broken (e.g. by
                // another mod's typo),
                // it won't cause a hard crash when building the tag registry natively.
                TagEntry tagEntry = TagEntry.optionalTag(cRL);

                // Add it to the list with 'alexscaves_compat' as the source.
                // We don't check for duplicates via .toString() because vanilla safely
                // deduplicates tags
                // via HashSets inside the tag Builder, and String allocation in this loop
                // creates unnecessary overhead.
                entries.add(new TagLoader.EntryWithSource(tagEntry, "alexscaves_compat"));
            }
        }
    }
}

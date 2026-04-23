package com.github.alexmodguy.alexscaves.mixin;

import net.minecraft.world.level.saveddata.maps.MapDecoration;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(MapDecoration.Type.class)
public class MapDecorationTypeMixin {

    @Shadow
    @Final
    @Mutable
    private static MapDecoration.Type[] $VALUES;

    @Invoker("<init>")
    public static MapDecoration.Type ac_invokeInit(String internalName, int internalId, boolean renderOnFrame,
            int mapColor, boolean trackCount) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void ac_addCustomValues(CallbackInfo ci) {
        ArrayList<MapDecoration.Type> variants = new ArrayList<MapDecoration.Type>(Arrays.asList($VALUES));
        variants.add(ac_invokeInit("AC_UNDERGROUND_CABIN", $VALUES.length, true, 0X6B6B6B, false));
        $VALUES = variants.toArray(new MapDecoration.Type[0]);
    }
}

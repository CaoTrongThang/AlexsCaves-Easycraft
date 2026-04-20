package com.github.alexmodguy.alexscaves.forge_shim.fml.common;

import com.github.alexmodguy.alexscaves.forge_shim.api.distmarker.Dist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventBusSubscriber {
    String modid() default "";

    Bus bus() default Bus.GAME;

    Dist[] value() default {};

    enum Bus {
        GAME,
        MOD
    }
}

package com.github.alexmodguy.alexscaves.fabric;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.neoforged.neoforge.entity.PartEntity;

public interface MultipartEntityLevel {

    Int2ObjectMap<PartEntity<?>> alexscaves$getPartEntityMap();
}

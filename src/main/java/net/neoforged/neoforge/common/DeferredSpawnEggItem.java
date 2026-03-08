package net.neoforged.neoforge.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DeferredSpawnEggItem extends SpawnEggItem {
    private final Supplier<? extends EntityType<? extends Mob>> type;
    private final int primaryColor;
    private final int secondaryColor;

    public DeferredSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Properties properties) {
        super(type.get(), primaryColor, secondaryColor, properties);
        this.type = type;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public EntityType<? extends Mob> getType(@Nullable Object context) {
        return type.get();
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    @Override
    public EntityType<?> getType(ItemStack stack) {
        return type.get();
    }
}

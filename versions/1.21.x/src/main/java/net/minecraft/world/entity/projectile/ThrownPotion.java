package net.minecraft.world.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownPotion extends AbstractThrownPotion {
    public ThrownPotion(Level level, LivingEntity livingEntity) {
        super(EntityType.SPLASH_POTION, level, livingEntity, new ItemStack(Items.SPLASH_POTION));
    }

    public ThrownPotion(Level level) {
        super(EntityType.SPLASH_POTION, level);
    }

    public ThrownPotion(Level level, double x, double y, double z) {
        super(EntityType.SPLASH_POTION, level, x, y, z, new ItemStack(Items.SPLASH_POTION));
    }

    @Override
    protected void onHitAsPotion(ServerLevel serverLevel, ItemStack itemStack, HitResult hitResult) {
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SPLASH_POTION;
    }
}

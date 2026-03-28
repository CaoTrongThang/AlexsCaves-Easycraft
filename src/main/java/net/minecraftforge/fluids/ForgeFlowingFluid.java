package net.minecraftforge.fluids;

public abstract class ForgeFlowingFluid extends BaseFlowingFluid {
    protected ForgeFlowingFluid(Properties properties) {
        super(properties);
    }

    public static class Properties extends BaseFlowingFluid.Properties {
        public Properties(java.util.function.Supplier<? extends FluidType> type, java.util.function.Supplier<? extends net.minecraft.world.level.material.Fluid> source, java.util.function.Supplier<? extends net.minecraft.world.level.material.Fluid> flowing) {
            super(type, source, flowing);
        }

        public Properties(net.minecraftforge.registries.DeferredHolder<? extends FluidType, ? extends FluidType> type, net.minecraftforge.registries.DeferredHolder<? extends net.minecraft.world.level.material.Fluid, ? extends net.minecraft.world.level.material.Fluid> source, net.minecraftforge.registries.DeferredHolder<? extends net.minecraft.world.level.material.Fluid, ? extends net.minecraft.world.level.material.Fluid> flowing) {
            super(type, source, flowing);
        }

        @Override
        public Properties bucket(java.util.function.Supplier<? extends net.minecraft.world.item.Item> bucket) {
            super.bucket(bucket);
            return this;
        }

        @Override
        public Properties block(java.util.function.Supplier<? extends net.minecraft.world.level.block.LiquidBlock> block) {
            super.block(block);
            return this;
        }
    }

    public static class Source extends BaseFlowingFluid.Source {
        public Source(Properties properties) {
            super(properties);
        }
    }

    public static class Flowing extends BaseFlowingFluid.Flowing {
        public Flowing(Properties properties) {
            super(properties);
        }
    }
}

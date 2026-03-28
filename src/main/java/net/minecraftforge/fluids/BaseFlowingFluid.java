package net.minecraftforge.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.DeferredHolder;

import java.util.function.Supplier;

public abstract class BaseFlowingFluid extends FlowingFluid {

    protected final Properties properties;

    protected BaseFlowingFluid(Properties properties) {
        this.properties = properties;
        this.registerDefaultState(this.getStateDefinition().any().setValue(FALLING, Boolean.FALSE));
    }

    @Override
    public Fluid getFlowing() {
        return properties.flowing.get();
    }

    @Override
    public Fluid getSource() {
        return properties.source.get();
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == getSource() || fluid == getFlowing();
    }

    @Override
    protected boolean canConvertToSource(Level level) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelReader) {
        return 4;
    }

    @Override
    protected int getDropOff(LevelReader levelReader) {
        return 1;
    }

    @Override
    public Item getBucket() {
        return properties.bucket == null ? Items.AIR : properties.bucket.get();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public Vec3 getFlow(BlockGetter blockGetter, BlockPos blockPos, FluidState fluidState) {
        return super.getFlow(blockGetter, blockPos, fluidState);
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    public FluidType getFluidType() {
        return properties.type.get();
    }

    @Override
    public float getHeight(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos) {
        return getOwnHeight(fluidState);
    }

    @Override
    public float getOwnHeight(FluidState fluidState) {
        return isSource(fluidState) ? 1.0F : (float) fluidState.getAmount() / 9.0F;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        if (properties.block == null) {
            return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        }
        BlockState state = properties.block.get().defaultBlockState();
        return state.hasProperty(LiquidBlock.LEVEL) ? state.setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState)) : state;
    }

    @Override
    public VoxelShape getShape(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.block();
    }

    public static class Properties {
        private final Supplier<? extends FluidType> type;
        private final Supplier<? extends Fluid> source;
        private final Supplier<? extends Fluid> flowing;
        private Supplier<? extends Item> bucket;
        private Supplier<? extends LiquidBlock> block;

        public Properties(Supplier<? extends FluidType> type, Supplier<? extends Fluid> source, Supplier<? extends Fluid> flowing) {
            this.type = type;
            this.source = source;
            this.flowing = flowing;
        }

        public Properties(DeferredHolder<? extends FluidType, ? extends FluidType> type, DeferredHolder<? extends Fluid, ? extends Fluid> source, DeferredHolder<? extends Fluid, ? extends Fluid> flowing) {
            this(type::get, source::get, flowing::get);
        }

        public Properties bucket(Supplier<? extends Item> bucket) {
            this.bucket = bucket;
            return this;
        }

        public Properties block(Supplier<? extends LiquidBlock> block) {
            this.block = block;
            return this;
        }
    }

    public static class Source extends BaseFlowingFluid {
        public Source(Properties properties) {
            super(properties);
            this.registerDefaultState(this.getStateDefinition().any().setValue(FALLING, Boolean.FALSE));
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }

    public static class Flowing extends BaseFlowingFluid {
        public Flowing(Properties properties) {
            super(properties);
            this.registerDefaultState(this.getStateDefinition().any().setValue(FALLING, Boolean.FALSE).setValue(LEVEL, 7));
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(new Property[]{LEVEL});
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }
}

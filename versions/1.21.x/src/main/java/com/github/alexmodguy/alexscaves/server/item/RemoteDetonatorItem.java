package com.github.alexmodguy.alexscaves.server.item;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.block.NuclearBombBlock;
import com.github.alexmodguy.alexscaves.server.misc.ACAdvancementTriggerRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACTagRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.neoforged.neoforge.common.world.chunk.TicketController;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RemoteDetonatorItem extends Item {

    public static final TicketController TICKET_CONTROLLER = new TicketController(ResourceLocation.fromNamespaceAndPath(AlexsCaves.MODID, "remote_detonator"));

    public RemoteDetonatorItem() {
        super(new Item.Properties().stacksTo(1));
    }

    public static boolean isActive(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return compoundTag.contains("BombDimension") || compoundTag.contains("BombPos");
    }

    private static Optional<ResourceKey<Level>> getBombDimension(CompoundTag tag) {
        return Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, tag.get("BombDimension")).result();
    }

    @Nullable
    public static GlobalPos getBombPosition(CompoundTag tag) {
        if (tag.contains("BombPos") && tag.contains("BombDimension")) {
            Optional<ResourceKey<Level>> dimension = getBombDimension(tag);
            if (dimension.isPresent()) {
                Optional<BlockPos> blockPos = NbtUtils.readBlockPos(tag, "BombPos");
                if (blockPos.isPresent()) {
                    return GlobalPos.of(dimension.get(), blockPos.get());
                }
            }
        }
        return null;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (isActive(itemStack)) {
            CompoundTag tag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            GlobalPos globalPos = getBombPosition(tag);
            if (globalPos != null && !level.isClientSide && level instanceof ServerLevel serverLevel) {
                ServerLevel dimensionLevel = serverLevel.getServer().getLevel(globalPos.dimension());
                if (dimensionLevel != null) {
                    loadChunksAround(dimensionLevel, player.getUUID(), globalPos.pos(), true);
                    if (dimensionLevel.getBlockState(globalPos.pos()).is(ACTagRegistry.REMOTE_DETONATOR_ACTIVATES)) {
                        if (dimensionLevel.getBlockState(globalPos.pos()).getBlock() instanceof NuclearBombBlock nuclearBombBlock) {
                            nuclearBombBlock.onCaughtFire(dimensionLevel.getBlockState(globalPos.pos()), dimensionLevel, globalPos.pos(), Direction.UP, player);
                            dimensionLevel.removeBlock(globalPos.pos(), false);
                        } else if (dimensionLevel.getBlockState(globalPos.pos()).getBlock() instanceof TntBlock) {
                            if (TntBlock.prime(dimensionLevel, globalPos.pos())) {
                                dimensionLevel.removeBlock(globalPos.pos(), false);
                            }
                        }
                        if (player.distanceToSqr(globalPos.pos().getCenter()) > 1000) {
                            ACAdvancementTriggerRegistry.REMOTE_DETONATION.get().triggerForEntity(player);
                        }
                        tag.remove("BombDimension");
                        tag.remove("BombPos");
                        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                    }
                }
            }
            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    private static void loadChunksAround(ServerLevel serverLevel, UUID ticket, BlockPos center, boolean load) {
        ChunkPos chunkPos = new ChunkPos(center);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                TICKET_CONTROLLER.forceChunk(serverLevel, center, chunkPos.x + i, chunkPos.z + j, load, true);
            }
        }
    }

    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean b) {
        if (!level.isClientSide && isActive(itemStack)) {
            CompoundTag compoundTag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            if (compoundTag.contains("BombTracked") && !compoundTag.getBoolean("BombTracked")) {
                return;
            }
            Optional<ResourceKey<Level>> dimension = getBombDimension(compoundTag);
            if (dimension.isPresent() && dimension.get() == level.dimension() && compoundTag.contains("BombPos")) {
                Optional<BlockPos> blockPos = NbtUtils.readBlockPos(compoundTag, "BombPos");
                if (blockPos.isEmpty()) {
                    return;
                }
                boolean clearTracking = false;
                if ((entity.tickCount + entity.getId()) % 20 == 0 && level.isLoaded(blockPos.get()) && !level.getBlockState(blockPos.get()).is(ACTagRegistry.REMOTE_DETONATOR_ACTIVATES)) {
                    clearTracking = true;
                }
                if (!level.isInWorldBounds(blockPos.get()) || clearTracking) {
                    compoundTag.remove("BombPos");
                    compoundTag.remove("BombDimension");
                    itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundTag));
                }
            }
        }
    }

    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        if (!level.getBlockState(blockPos).is(ACTagRegistry.REMOTE_DETONATOR_ACTIVATES)) {
            return super.useOn(context);
        }
        level.playSound(null, blockPos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        boolean reuseStack = player != null && !player.getAbilities().instabuild && itemStack.getCount() == 1;
        if (reuseStack) {
            CompoundTag tag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            this.addBombTags(level.dimension(), blockPos, tag);
            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        } else {
            ItemStack copiedStack = new ItemStack(ACItemRegistry.REMOTE_DETONATOR.get(), 1);
            CompoundTag tag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            this.addBombTags(level.dimension(), blockPos, tag);
            copiedStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            itemStack.shrink(1);
            if (player != null) {
                if (!player.getInventory().add(copiedStack)) {
                    player.drop(copiedStack, false);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void addBombTags(ResourceKey<Level> levelResourceKey, BlockPos blockPos, CompoundTag tag) {
        tag.put("BombPos", NbtUtils.writeBlockPos(blockPos));
        Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, levelResourceKey).result().ifPresent(encoded -> tag.put("BombDimension", encoded));
        tag.putBoolean("BombTracked", true);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (tag.contains("BombPos")) {
            Optional<ResourceKey<Level>> dimension = getBombDimension(tag);
            Optional<BlockPos> blockPos = NbtUtils.readBlockPos(tag, "BombPos");
            if (dimension.isPresent() && blockPos.isPresent()) {
                tooltip.add(Component.translatable("item.alexscaves.remote_detonator.desc", blockPos.get().getX(), blockPos.get().getY(), blockPos.get().getZ()).withStyle(ChatFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, context, tooltip, flagIn);
    }
}

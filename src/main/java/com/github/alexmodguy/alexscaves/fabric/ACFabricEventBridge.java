package com.github.alexmodguy.alexscaves.fabric;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ACFabricEventBridge {

    private ACFabricEventBridge() {
    }

    public static void registerCommon() {
        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (level.isClientSide) {
                return InteractionResult.PASS;
            }
            PlayerInteractEvent.EntityInteract event = new PlayerInteractEvent.EntityInteract(
                player,
                level,
                hand,
                player.getItemInHand(hand),
                entity
            );
            MinecraftForge.EVENT_BUS.post(event);
            return event.isCanceled() ? event.getCancellationResult() : InteractionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (level.isClientSide) {
                return InteractionResultHolder.pass(player.getItemInHand(hand));
            }
            PlayerInteractEvent.RightClickItem event = new PlayerInteractEvent.RightClickItem(
                player,
                level,
                hand,
                player.getItemInHand(hand)
            );
            MinecraftForge.EVENT_BUS.post(event);
            if (!event.isCanceled()) {
                return InteractionResultHolder.pass(event.getItemStack());
            }
            return new InteractionResultHolder<>(event.getCancellationResult(), event.getItemStack());
        });

        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (level.isClientSide) {
                return InteractionResult.PASS;
            }
            AttackEntityEvent event = new AttackEntityEvent(player, entity);
            MinecraftForge.EVENT_BUS.post(event);
            return event.isCanceled() ? InteractionResult.FAIL : InteractionResult.PASS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var player : server.getPlayerList().getPlayers()) {
                MinecraftForge.EVENT_BUS.post(new TickEvent.PlayerTickEvent(player, TickEvent.Phase.END));
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
            MinecraftForge.EVENT_BUS.post(new PlayerEvent.PlayerLoggedInEvent(handler.getPlayer()))
        );

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 2, offers -> {
            Map<Integer, List<VillagerTrades.ItemListing>> tradeMap = new HashMap<>();
            tradeMap.put(2, offers);
            MinecraftForge.EVENT_BUS.post(new VillagerTradesEvent(VillagerProfession.CARTOGRAPHER, tradeMap));
        });

        TradeOfferHelper.registerWanderingTraderOffers(1, offers ->
            MinecraftForge.EVENT_BUS.post(new WandererTradesEvent(offers))
        );

        ServerLifecycleEvents.SERVER_STARTING.register(server ->
            MinecraftForge.EVENT_BUS.post(new ServerAboutToStartEvent(server))
        );
        ServerLifecycleEvents.SERVER_STOPPING.register(server ->
            MinecraftForge.EVENT_BUS.post(new ServerStoppingEvent(server))
        );
    }
}

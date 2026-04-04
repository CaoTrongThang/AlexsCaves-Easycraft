package com.github.alexmodguy.alexscaves.fabric;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.ArrayList;
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
            NeoForge.EVENT_BUS.post(event);
            return event.isCanceled() ? event.getCancellationResult() : InteractionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (level.isClientSide) {
                return InteractionResult.PASS;
            }
            PlayerInteractEvent.RightClickItem event = new PlayerInteractEvent.RightClickItem(
                player,
                level,
                hand,
                player.getItemInHand(hand)
            );
            NeoForge.EVENT_BUS.post(event);
            if (event.getItemStack() != player.getItemInHand(hand)) {
                player.setItemInHand(hand, event.getItemStack());
            }
            return event.isCanceled() ? event.getCancellationResult() : InteractionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (level.isClientSide) {
                return InteractionResult.PASS;
            }
            AttackEntityEvent event = new AttackEntityEvent(player, entity);
            NeoForge.EVENT_BUS.post(event);
            return event.isCanceled() ? InteractionResult.FAIL : InteractionResult.PASS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var player : server.getPlayerList().getPlayers()) {
                NeoForge.EVENT_BUS.post(new PlayerTickEvent.Post(player));
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
            NeoForge.EVENT_BUS.post(new PlayerEvent.PlayerLoggedInEvent(handler.getPlayer()))
        );

        VillagerProfession cartographer = BuiltInRegistries.VILLAGER_PROFESSION.getValue(VillagerProfession.CARTOGRAPHER.location());
        if (cartographer != null) {
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 2, offers -> {
                Map<Integer, List<VillagerTrades.ItemListing>> tradeMap = new HashMap<>();
                tradeMap.put(2, offers);
                NeoForge.EVENT_BUS.post(new VillagerTradesEvent(cartographer, tradeMap));
            });
        }

        TradeOfferHelper.registerWanderingTraderOffers(builder -> {
            List<VillagerTrades.ItemListing> offers = new ArrayList<>();
            NeoForge.EVENT_BUS.post(new WandererTradesEvent(offers));
            builder.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL, offers);
        });
    }
}

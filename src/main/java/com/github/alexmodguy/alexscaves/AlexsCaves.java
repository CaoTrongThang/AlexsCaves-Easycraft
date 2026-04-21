package com.github.alexmodguy.alexscaves;

import com.github.alexmodguy.alexscaves.client.config.ACClientConfig;
import com.github.alexmodguy.alexscaves.config.ACModConfigSpec;
import com.github.alexmodguy.alexscaves.server.CommonProxy;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.config.ACServerConfig;
import com.github.alexmodguy.alexscaves.server.config.BiomeGenerationConfig;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRegistry;
import com.github.alexmodguy.alexscaves.server.level.storage.ACWorldData;
import com.github.alexmodguy.alexscaves.server.level.surface.ACSurfaceRules;
import com.github.alexmodguy.alexscaves.server.message.ArmorKeyMessage;
import com.github.alexmodguy.alexscaves.server.message.BeholderRotateMessage;
import com.github.alexmodguy.alexscaves.server.message.BeholderSyncMessage;
import com.github.alexmodguy.alexscaves.server.message.MountedEntityKeyMessage;
import com.github.alexmodguy.alexscaves.server.message.MultipartEntityMessage;
import com.github.alexmodguy.alexscaves.server.message.PlayerJumpFromMagnetMessage;
import com.github.alexmodguy.alexscaves.server.message.PossessionKeyMessage;
import com.github.alexmodguy.alexscaves.server.message.SpelunkeryTableChangeMessage;
import com.github.alexmodguy.alexscaves.server.message.SpelunkeryTableCompleteTutorialMessage;
import com.github.alexmodguy.alexscaves.server.message.SundropRainbowMessage;
import com.github.alexmodguy.alexscaves.server.message.UpdateBossBarMessage;
import com.github.alexmodguy.alexscaves.server.message.UpdateBossEruptionStatus;
import com.github.alexmodguy.alexscaves.server.message.UpdateCaveBiomeMapTagMessage;
import com.github.alexmodguy.alexscaves.server.message.UpdateEffectVisualityEntityMessage;
import com.github.alexmodguy.alexscaves.server.message.UpdateItemTagMessage;
import com.github.alexmodguy.alexscaves.server.message.WorldEventMessage;
import com.github.alexmodguy.alexscaves.server.misc.ACAdvancementTriggerRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACLoadedMods;
import com.github.alexmodguy.alexscaves.server.misc.ACPlayerCapes;
import com.github.alexmodguy.alexscaves.server.misc.ACPotPatternRegistry;
import com.github.alexmodguy.alexscaves.server.misc.WebHelper;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import com.github.alexmodguy.alexscaves.forge_shim.common.world.ForgeChunkManager;
import com.github.alexmodguy.alexscaves.forge_shim.network.NetworkDirection;
import com.github.alexmodguy.alexscaves.forge_shim.network.simple.SimpleChannel;
import com.github.alexmodguy.alexscaves.forge_shim.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlexsCaves {
        public static final String MODID = "alexscaves";
        public static final Logger LOGGER = LogUtils.getLogger();
        public static final String VERSION = "2.0.2-fabric";

        public static CommonProxy PROXY = new CommonProxy();
        public static final SimpleChannel NETWORK_WRAPPER = new SimpleChannel(
                        new ResourceLocation(MODID, "main_channel"));
        public static final ACServerConfig COMMON_CONFIG;
        public static final ACClientConfig CLIENT_CONFIG;
        public static final List<String> MOD_GENERATION_CONFLICTS = new ArrayList<>();
        private static boolean initialized;
        private static boolean worldgenBootstrapped;

        static {
                final Pair<ACServerConfig, ACModConfigSpec> serverPair = new ACModConfigSpec.Builder()
                                .configure(ACServerConfig::new);
                COMMON_CONFIG = serverPair.getLeft();

                final Pair<ACClientConfig, ACModConfigSpec> clientPair = new ACModConfigSpec.Builder()
                                .configure(ACClientConfig::new);
                CLIENT_CONFIG = clientPair.getLeft();
        }

        public static void setProxy(CommonProxy proxy) {
                PROXY = proxy;
        }

        public static void init() {
                if (initialized) {
                        return;
                }
                initialized = true;

                BiomeGenerationConfig.reloadConfig();
                bootstrapWorldgen();
                PROXY.initPathfinding();

                int packetsRegistered = 0;
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, SpelunkeryTableChangeMessage.class,
                                SpelunkeryTableChangeMessage::write, SpelunkeryTableChangeMessage::read,
                                SpelunkeryTableChangeMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, SpelunkeryTableCompleteTutorialMessage.class,
                                SpelunkeryTableCompleteTutorialMessage::write,
                                SpelunkeryTableCompleteTutorialMessage::read,
                                SpelunkeryTableCompleteTutorialMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, PlayerJumpFromMagnetMessage.class,
                                PlayerJumpFromMagnetMessage::write, PlayerJumpFromMagnetMessage::read,
                                PlayerJumpFromMagnetMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, MultipartEntityMessage.class,
                                MultipartEntityMessage::write, MultipartEntityMessage::read,
                                MultipartEntityMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, MountedEntityKeyMessage.class,
                                MountedEntityKeyMessage::write, MountedEntityKeyMessage::read,
                                MountedEntityKeyMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, UpdateEffectVisualityEntityMessage.class,
                                UpdateEffectVisualityEntityMessage::write, UpdateEffectVisualityEntityMessage::read,
                                UpdateEffectVisualityEntityMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, PossessionKeyMessage.class,
                                PossessionKeyMessage::write,
                                PossessionKeyMessage::read, PossessionKeyMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, UpdateItemTagMessage.class,
                                UpdateItemTagMessage::write,
                                UpdateItemTagMessage::read, UpdateItemTagMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, BeholderSyncMessage.class,
                                BeholderSyncMessage::write,
                                BeholderSyncMessage::read, BeholderSyncMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, BeholderRotateMessage.class,
                                BeholderRotateMessage::write,
                                BeholderRotateMessage::read, BeholderRotateMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, ArmorKeyMessage.class, ArmorKeyMessage::write,
                                ArmorKeyMessage::read, ArmorKeyMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, WorldEventMessage.class, WorldEventMessage::write,
                                WorldEventMessage::read, WorldEventMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, UpdateCaveBiomeMapTagMessage.class,
                                UpdateCaveBiomeMapTagMessage::write, UpdateCaveBiomeMapTagMessage::read,
                                UpdateCaveBiomeMapTagMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, UpdateBossEruptionStatus.class,
                                UpdateBossEruptionStatus::write, UpdateBossEruptionStatus::read,
                                UpdateBossEruptionStatus::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, UpdateBossBarMessage.class,
                                UpdateBossBarMessage::write,
                                UpdateBossBarMessage::read, UpdateBossBarMessage::handle);
                NETWORK_WRAPPER.registerMessage(packetsRegistered++, SundropRainbowMessage.class,
                                SundropRainbowMessage::write,
                                SundropRainbowMessage::read, SundropRainbowMessage::handle);

                ACPlayerCapes.setup();
                ACBlockRegistry.setup();
                ACItemRegistry.setup();
                ACAdvancementTriggerRegistry.setup();
                ACPotPatternRegistry.expandVanillaDefinitions();
                ACBlockEntityRegistry.expandVanillaDefinitions();
                ForgeChunkManager.setForcedChunkLoadingCallback(MODID, ACWorldData::clearLoadedChunksCallback);
                ACLoadedMods.afterAllModsLoaded();
                readModIncompatibilities();
        }

        public static void initClient() {
                PROXY.commonInit();
                PROXY.clientInit();
        }

        public static <MSG> void sendMSGToServer(MSG message) {
                NETWORK_WRAPPER.sendToServer(message);
        }

        public static <MSG> void sendMSGToAll(MSG message) {
                if (ServerLifecycleHooks.getCurrentServer() == null) {
                        return;
                }
                for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                        sendNonLocal(message, player);
                }
        }

        public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
                NETWORK_WRAPPER.sendTo(msg, player, NetworkDirection.PLAY_TO_CLIENT);
        }

        private static void readModIncompatibilities() {
                MOD_GENERATION_CONFLICTS.clear();
                BufferedReader urlContents = WebHelper.getURLContents(
                                "https://raw.githubusercontent.com/AlexModGuy/AlexsCaves/main/src/main/resources/assets/alexscaves/warning/mod_generation_conflicts.txt",
                                "assets/alexscaves/warning/mod_generation_conflicts.txt");
                if (urlContents == null) {
                        LOGGER.warn("Failed to load mod conflicts");
                        return;
                }
                try {
                        String line;
                        while ((line = urlContents.readLine()) != null) {
                                MOD_GENERATION_CONFLICTS.add(line);
                        }
                } catch (IOException e) {
                        LOGGER.warn("Failed to load mod conflicts", e);
                }
        }

        private static void bootstrapWorldgen() {
                if (worldgenBootstrapped) {
                        return;
                }
                worldgenBootstrapped = true;
                ACBiomeRegistry.init();
                ACSurfaceRules.setup();
        }
}

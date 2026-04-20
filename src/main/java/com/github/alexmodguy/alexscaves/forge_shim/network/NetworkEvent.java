package com.github.alexmodguy.alexscaves.forge_shim.network;

import net.minecraft.server.level.ServerPlayer;

public final class NetworkEvent {
    private NetworkEvent() {
    }

    public static class Context {
        private final ServerPlayer sender;
        private final NetworkDirection direction;

        public Context(ServerPlayer sender, NetworkDirection direction) {
            this.sender = sender;
            this.direction = direction;
        }

        public ServerPlayer getSender() {
            return sender;
        }

        public NetworkDirection getDirection() {
            return direction;
        }

        public void enqueueWork(Runnable runnable) {
            runnable.run();
        }

        public void setPacketHandled(boolean handled) {
        }
    }
}

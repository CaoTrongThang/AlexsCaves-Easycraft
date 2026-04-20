package com.github.alexthe666.citadel.server.message;

import com.github.alexthe666.citadel.client.render.pathfinding.PathfindingDebugRenderer;
import com.github.alexthe666.citadel.server.entity.pathfinding.raycoms.MNode;
import net.minecraft.network.FriendlyByteBuf;
import com.github.alexmodguy.alexscaves.forge_shim.network.NetworkDirection;
import com.github.alexmodguy.alexscaves.forge_shim.network.NetworkEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class SyncePathMessage {
    public Set<MNode> lastDebugNodesVisited = new HashSet<>();
    public Set<MNode> lastDebugNodesNotVisited = new HashSet<>();
    public Set<MNode> lastDebugNodesPath = new HashSet<>();

    public SyncePathMessage(Set<MNode> visited, Set<MNode> notVisited, Set<MNode> path) {
        this.lastDebugNodesVisited = visited;
        this.lastDebugNodesNotVisited = notVisited;
        this.lastDebugNodesPath = path;
    }

    public void write(final FriendlyByteBuf buf) {
        buf.writeInt(lastDebugNodesVisited.size());
        for (final MNode node : lastDebugNodesVisited) {
            node.serializeToBuf(buf);
        }

        buf.writeInt(lastDebugNodesNotVisited.size());
        for (final MNode node : lastDebugNodesNotVisited) {
            node.serializeToBuf(buf);
        }

        buf.writeInt(lastDebugNodesPath.size());
        for (final MNode node : lastDebugNodesPath) {
            node.serializeToBuf(buf);
        }
    }

    public static SyncePathMessage read(final FriendlyByteBuf buf) {
        int size = buf.readInt();
        Set<MNode> visited = new HashSet<>();
        for (int i = 0; i < size; i++) {
            visited.add(new MNode(buf));
        }

        size = buf.readInt();
        Set<MNode> notVisited = new HashSet<>();
        for (int i = 0; i < size; i++) {
            notVisited.add(new MNode(buf));
        }

        size = buf.readInt();
        Set<MNode> path = new HashSet<>();
        for (int i = 0; i < size; i++) {
            path.add(new MNode(buf));
        }
        return new SyncePathMessage(visited, notVisited, path);
    }

    public static class Handler {
        public static boolean handle(SyncePathMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            contextSupplier.get().enqueueWork(() -> {
                contextSupplier.get().setPacketHandled(true);
                if (contextSupplier.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                    PathfindingDebugRenderer.lastDebugNodesVisited = message.lastDebugNodesVisited;
                    PathfindingDebugRenderer.lastDebugNodesNotVisited = message.lastDebugNodesNotVisited;
                    PathfindingDebugRenderer.lastDebugNodesPath = message.lastDebugNodesPath;
                }
            });
            return true;
        }
    }
}

package net.minecraftforge.network;

import net.minecraftforge.fml.LogicalSide;

public enum NetworkDirection {
    PLAY_TO_CLIENT(LogicalSide.CLIENT),
    PLAY_TO_SERVER(LogicalSide.SERVER);

    private final LogicalSide receptionSide;

    NetworkDirection(LogicalSide receptionSide) {
        this.receptionSide = receptionSide;
    }

    public LogicalSide getReceptionSide() {
        return receptionSide;
    }
}

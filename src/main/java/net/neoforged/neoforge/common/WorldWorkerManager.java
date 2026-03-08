package net.neoforged.neoforge.common;

import java.util.ArrayList;
import java.util.List;

public final class WorldWorkerManager {
    private static final List<IWorker> WORKERS = new ArrayList<>();

    private WorldWorkerManager() {
    }

    public static void addWorker(IWorker worker) {
        WORKERS.add(worker);
    }

    public interface IWorker {
        boolean hasWork();

        boolean doWork();
    }
}

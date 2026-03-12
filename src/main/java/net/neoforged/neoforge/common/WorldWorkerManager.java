package net.neoforged.neoforge.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class WorldWorkerManager {
    private static final List<IWorker> WORKERS = new ArrayList<>();

    private WorldWorkerManager() {
    }

    static {
        ServerTickEvents.END_SERVER_TICK.register(server -> tickWorkers());
    }

    public static void addWorker(IWorker worker) {
        WORKERS.add(worker);
    }

    private static void tickWorkers() {
        Iterator<IWorker> iterator = WORKERS.iterator();
        while (iterator.hasNext()) {
            IWorker worker = iterator.next();
            if (!worker.hasWork() || !worker.doWork()) {
                iterator.remove();
            }
        }
    }

    public interface IWorker {
        boolean hasWork();

        boolean doWork();
    }
}

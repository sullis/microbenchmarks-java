package io.github.sullis.microbenchmarks;

import java.util.concurrent.locks.LockSupport;

public class CachedLongAdderCounter extends LongAdderCounter {
    private volatile long snapshotValue = 0;

    private final Thread updater = new Thread("CachedLongAdderCounter Updater Thread") {
        @Override
        public void run() {
            while (true) {
                snapshotValue = CachedLongAdderCounter.super.longValue();

                // avoid explicit dependency on sun.misc.Util
                LockSupport.parkNanos(1000 * 1000);
            }
        }
    };

    public CachedLongAdderCounter() {
        snapshotValue = 0;
        updater.setDaemon(true);
        updater.start();
    }

    @Override
    public long longValue() {
        return snapshotValue;
    }

}

package io.github.sullis.microbenchmarks;

import java.util.concurrent.locks.LockSupport;

public class CachedLongAdderCounter extends LongAdderCounter {
    private volatile long snapshotValue = 0;
    private final Thread updater;

    public CachedLongAdderCounter(final long updateIntervalMillis) {
        if (updateIntervalMillis < 1) {
            throw new IllegalArgumentException("updateIntervalMillis=" + updateIntervalMillis);
        }

        snapshotValue = 0;
        updater = new Thread("CachedLongAdderCounter Updater Thread") {
            @Override
            public void run() {
                while (true) {
                    snapshotValue = CachedLongAdderCounter.super.longValue();
                    LockSupport.parkNanos(1000 * updateIntervalMillis);
                }
            }
        };
        updater.setDaemon(true);
        updater.start();
    }

    @Override
    public long longValue() {
        return snapshotValue;
    }

}

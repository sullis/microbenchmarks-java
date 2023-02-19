package io.github.sullis.microbenchmarks;

import java.time.Duration;
import java.util.concurrent.locks.LockSupport;

public class CachedLongAdderCounter extends LongAdderCounter {
    private volatile long snapshotValue = 0;
    private final Thread updater;

    public CachedLongAdderCounter(final Duration updateDuration) {
        final long updateDurationNanos = updateDuration.toNanos();
        if (updateDurationNanos < 1) {
            throw new IllegalArgumentException("updateDurationNanos=" + updateDurationNanos);
        }

        snapshotValue = 0;
        updater = new Thread("CachedLongAdderCounter Updater Thread") {
            @Override
            public void run() {
                while (true) {
                    snapshotValue = CachedLongAdderCounter.super.longValue();
                    LockSupport.parkNanos(updateDurationNanos);
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

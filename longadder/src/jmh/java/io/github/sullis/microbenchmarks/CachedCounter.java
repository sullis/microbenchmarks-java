package io.github.sullis.microbenchmarks;

import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.CoarseCachedClock;

public class CachedCounter implements Counter {
    private final Counter counter;
    private final Clock clock = CoarseCachedClock.instance();
    private volatile long snapshotValue = 0;
    private volatile long snapshotTime;

    public CachedCounter(final Counter c) {
        this.counter = c;
        this.snapshotValue = this.counter.longValue();
        this.snapshotTime = clock.currentTimeMillis();
    }

    @Override
    public void increment() {
        this.counter.increment();
    }

    @Override
    public void decrement() {
        this.counter.decrement();
    }

    @Override
    public long longValue() {
        final long now = this.clock.currentTimeMillis();
        final long age = now - snapshotTime;
        if (age > 0) {
            snapshotValue = this.counter.longValue();
            snapshotTime = now;
        }
        return snapshotValue;
    }
}

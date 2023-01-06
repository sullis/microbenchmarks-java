package io.github.sullis.microbenchmarks;

import java.time.Duration;

public class CachedCounter implements Counter {
    private final Counter counter;
    private final long maxAgeMillis;
    private volatile long cachedValue = 0;
    private volatile long snapshotTime = 0;

    public CachedCounter(final Counter c, final Duration maxAge) {
        this.counter = c;
        this.maxAgeMillis = maxAge.toMillis();
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
        final long now = System.currentTimeMillis();
        final long age = now - snapshotTime;
        if (age > maxAgeMillis) {
            cachedValue = this.counter.longValue();
            snapshotTime = now;
        }
        return cachedValue;
    }
}

package io.github.sullis.microbenchmarks;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongCounter implements Counter {
    private final AtomicLong atomicLong = new AtomicLong();

    @Override
    public void increment() {
        atomicLong.incrementAndGet();
    }

    @Override
    public void decrement() {
        atomicLong.decrementAndGet();
    }

    @Override
    public long longValue() {
        return atomicLong.longValue();
    }
}

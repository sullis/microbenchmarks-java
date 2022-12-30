package io.github.sullis.microbenchmarks;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounter implements Counter {
    private AtomicInteger atomicInteger = new AtomicInteger();

    public AtomicIntegerCounter() { }

    @Override
    public void increment() {
        atomicInteger.incrementAndGet();
    }

    @Override
    public void decrement() {
        atomicInteger.decrementAndGet();
    }

    @Override
    public long longValue() {
        return atomicInteger.longValue();
    }
}

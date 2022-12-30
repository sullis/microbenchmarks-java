package io.github.sullis.microbenchmarks;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderCounter implements Counter {
    private final LongAdder longAdder = new LongAdder();

    public LongAdderCounter() { }

    @Override
    public void increment() {
        longAdder.increment();
    }

    @Override
    public void decrement() {
        longAdder.decrement();
    }

    @Override
    public long longValue() {
        return longAdder.sum();
    }
}

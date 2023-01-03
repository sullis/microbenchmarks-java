package io.github.sullis.microbenchmarks;

import org.agrona.concurrent.UnsafeBuffer;
import org.agrona.concurrent.status.AtomicCounter;

import static java.nio.ByteBuffer.allocateDirect;
import static org.agrona.concurrent.status.CountersReader.COUNTER_LENGTH;

// https://github.com/real-logic/agrona
public class AgronaAtomicCounter implements Counter {
    private final AtomicCounter counter;

    public AgronaAtomicCounter() {
        counter = new AtomicCounter(new UnsafeBuffer(allocateDirect(10 * COUNTER_LENGTH)), 1);
    }

    @Override
    public void increment() {
        counter.increment();
    }

    @Override
    public void decrement() {
        counter.decrement();
    }

    @Override
    public long longValue() {
        return counter.get();
    }
}

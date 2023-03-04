
package io.github.sullis.microbenchmarks;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Threads(value = Threads.MAX)
public class LongAdderBenchmark {
    public enum CounterSupplier {
        ATOMIC_INTEGER(new AtomicIntegerCounter()),
        ATOMIC_LONG(new AtomicLongCounter()),
        LONG_ADDER(new LongAdderCounter()),
        CACHED_LONG_ADDER_10_MS(new CachedLongAdderCounter(Duration.ofMillis(10)));

        private final Counter counter;

        private
        CounterSupplier(final Counter c) {
            this.counter = c;
        }

        public Counter get() {
            return this.counter;
        }
    }

    @Param
    private CounterSupplier counterSupplier;

    @Benchmark
    public void incrementOnly(final Blackhole bh) {
        Counter c = this.counterSupplier.get();
        c.increment();
        bh.consume(c);
    }

    @Benchmark
    public void decrementOnly(final Blackhole bh) {
        Counter c = this.counterSupplier.get();
        c.decrement();
        bh.consume(c);
    }

    @Benchmark
    public void getOnly(final Blackhole bh) {
        Counter c = this.counterSupplier.get();
        bh.consume(c.longValue());
    }

    @Benchmark
    public void incrementDecrement(final Blackhole bh) {
        Counter c = this.counterSupplier.get();
        c.increment();
        c.decrement();
        bh.consume(c);
    }

    @Benchmark
    public void incrementDecrementGet(final Blackhole bh) {
        Counter c = this.counterSupplier.get();
        c.increment();
        c.decrement();
        bh.consume(c.longValue());
    }

    @Benchmark
    public void getIncrementDecrement(final Blackhole bh) {
        Counter c = this.counterSupplier.get();
        bh.consume(c.longValue());
        c.increment();
        c.decrement();
    }

    @Benchmark
    public void incrementGetDecrementGet(final Blackhole bh) {
        Counter c = this.counterSupplier.get();
        c.increment();
        bh.consume(c.longValue());
        c.decrement();
        bh.consume(c.longValue());
    }

    public static class CachedLongAdderCounter extends LongAdderCounter {
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

    public static interface Counter {
        void increment();
        void decrement();
        long longValue();
    }

    public static class LongAdderCounter implements Counter {
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

    public static class AtomicIntegerCounter implements Counter {
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

    public static class AtomicLongCounter implements Counter {
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
}

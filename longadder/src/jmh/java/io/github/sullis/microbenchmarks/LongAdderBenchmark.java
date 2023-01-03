
package io.github.sullis.microbenchmarks;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class LongAdderBenchmark {
    public enum CounterSupplier {
        ATOMIC_INTEGER(new AtomicIntegerCounter()),
        ATOMIC_LONG(new AtomicLongCounter()),
        LONG_ADDER(new LongAdderCounter()),
        AGRONA_ATOMIC_COUNTER(new AgronaAtomicCounter());

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
}


package io.github.sullis.microbenchmarks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class LongAdderBenchmark {
    public enum CounterSupplier {
        ATOMIC_INTEGER(new AtomicIntegerCounter()),
        ATOMIC_LONG(new AtomicLongCounter()),
        LONG_ADDER(new LongAdderCounter());

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

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void testCounter(final Blackhole bh) {
        Counter c = this.counterSupplier.get();
        c.increment();
        bh.consume(c.longValue());
        c.decrement();
        bh.consume(c.longValue());
    }
}

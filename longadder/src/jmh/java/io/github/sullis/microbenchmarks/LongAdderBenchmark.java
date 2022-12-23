
package io.github.sullis.microbenchmarks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class LongAdderBenchmark {
    private static final LongAdder longAdder = new LongAdder();
    private static final AtomicLong atomicLong = new AtomicLong();
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void testLongAdder(final Blackhole bh) {
        longAdder.increment();
        bh.consume(longAdder.longValue());
        longAdder.decrement();
        bh.consume(longAdder.longValue());
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void testAtomicLong(final Blackhole bh) {
        atomicLong.incrementAndGet();
        bh.consume(atomicLong.longValue());
        atomicLong.decrementAndGet();
        bh.consume(atomicLong.longValue());
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void testAtomicInteger(final Blackhole bh) {
        atomicInteger.incrementAndGet();
        bh.consume(atomicInteger.intValue());
        atomicInteger.decrementAndGet();
        bh.consume(atomicInteger.intValue());
    }
}

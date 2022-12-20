
package io.github.sullis.microbenchmarks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class LongAdderBenchmark {
    private LongAdder longAdder;

    @Setup
    public void setUp() throws Exception {
        longAdder = new LongAdder();
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void testLongAdder() {
        longAdder.increment();
        final long afterIncrement = longAdder.longValue();
        longAdder.decrement();
        final long afterDecrement = longAdder.longValue();
    }
}

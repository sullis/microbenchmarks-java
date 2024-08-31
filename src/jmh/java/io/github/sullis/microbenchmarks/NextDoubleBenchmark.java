
package io.github.sullis.microbenchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@Threads(value = Threads.MAX)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class NextDoubleBenchmark {
    @Benchmark
    public void threadLocalRandom(final Blackhole bh) {
        bh.consume(ThreadLocalRandom.current().nextDouble());
    }

    @Benchmark
    public void mathRandom(final Blackhole bh) {
        bh.consume(Math.random());
    }
}

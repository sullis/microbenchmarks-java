
package io.github.sullis.microbenchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import java.util.function.DoubleSupplier;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@Threads(value = Threads.MAX)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class NextDoubleBenchmark {
    @Param
    private NextDoubleSupplier nextDoubleSupplier;

    @Benchmark
    public void nextDouble(final Blackhole bh) {
        bh.consume(this.nextDoubleSupplier.nextDouble());
    }

    public enum NextDoubleSupplier {
        THREAD_LOCAL_RANDOM(() -> ThreadLocalRandom.current().nextDouble()),
        JAVA_MATH_RANDOM(() -> Math.random());

        private final DoubleSupplier supplier;

        NextDoubleSupplier(DoubleSupplier supplier) {
            this.supplier = supplier;
        }

        public double nextDouble() {
            return this.supplier.getAsDouble();
        }
    }
}

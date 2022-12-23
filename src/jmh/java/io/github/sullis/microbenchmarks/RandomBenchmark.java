
package io.github.sullis.microbenchmarks;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class RandomBenchmark {
    private static final Supplier<Random> javaUtilRandomSupplier = new Supplier<Random>() {
        private static Random random = new java.util.Random();
        @Override
        public Random get() {
            return random;
        }
    };

    private static final Supplier<Random> threadLocalRandomSupplier = new Supplier<Random>() {
        @Override
        public Random get() {
            return ThreadLocalRandom.current();
        }
    };

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void measureJavaUtilRandom(final Blackhole bh) {
        exerciseRandom(bh, javaUtilRandomSupplier);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void measureThreadLocalRandom(final Blackhole bh) {
        exerciseRandom(bh, threadLocalRandomSupplier);
    }

    private void exerciseRandom(final Blackhole bh, final Supplier<Random> randomSupplier) {
        final Random r = randomSupplier.get();
        bh.consume(r.nextInt());
        bh.consume(r.nextLong());
        bh.consume(r.nextFloat());
        bh.consume(r.nextDouble());
        bh.consume(r.nextBoolean());
    }

}


package io.github.sullis.microbenchmarks;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class RandomBenchmark {
    @Param
    private RandomSupplier randomSupplier;

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void measureRandom(final Blackhole bh) {
        final Random r = this.randomSupplier.get();
        bh.consume(r.nextInt());
    }

    private static final Supplier<java.util.Random> javaUtilRandomSupplier = new Supplier<Random>() {
        private static Random random = new java.util.Random();
        @Override
        public Random get() {
            return random;
        }
    };

    private static final Supplier<java.util.Random> threadLocalRandomSupplier = new Supplier<Random>() {
        @Override
        public Random get() {
            return ThreadLocalRandom.current();
        }
    };

    public enum RandomSupplier {
        JAVA_UTIL_RANDOM(javaUtilRandomSupplier),
        THREAD_LOCAL_RANDOM(threadLocalRandomSupplier);

        private final Supplier<java.util.Random> supplier;

        RandomSupplier(Supplier<java.util.Random> randomSupplier) {
            this.supplier = randomSupplier;
        }

        public java.util.Random get() {
            return this.supplier.get();
        }
    }

}

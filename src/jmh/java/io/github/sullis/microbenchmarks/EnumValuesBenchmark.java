
package io.github.sullis.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class EnumValuesBenchmark {

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    @Threads(1)
    public void enumValuesArray(final Blackhole bh) {
        bh.consume(Fruit.values().length);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    @Threads(1)
    public void staticEnumValuesArray(final Blackhole bh) {
        bh.consume(Fruit.STATIC_FRUIT_VALUES.length);
    }

    public enum Fruit {
        APPLE("Apple"),
        BANANA("Banana"),
        ORANGE("Orange"),
        STRAWBERRY("Strawberry");

        public static final Fruit[] STATIC_FRUIT_VALUES = values();

        private String id;

        Fruit(String id) {
           this.id = id;
        }

    }
}
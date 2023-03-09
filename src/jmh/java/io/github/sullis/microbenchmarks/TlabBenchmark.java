
package io.github.sullis.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class TlabBenchmark {

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    @Threads(1)
    @Fork(jvmArgs = "-XX:+UseTLAB")
    public void tlabOneObject(final Blackhole bh) {
        bh.consume(new Object());
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    @Threads(1)
    @Fork(jvmArgs = "-XX:-UseTLAB")
    public void noTlabOneObject(final Blackhole bh) {
        bh.consume(new Object());
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    @Threads(1)
    @Fork(jvmArgs = "-XX:+UseTLAB")
    public void tlabOneEnumValuesArray(final Blackhole bh) {
        bh.consume(Fruit.values());
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    @Threads(1)
    @Fork(jvmArgs = "-XX:-UseTLAB")
    public void noTlabOneEnumValuesArray(final Blackhole bh) {
        bh.consume(Fruit.values());
    }

    public enum Fruit {
        APPLE("Apple"),
        BANANA("Banana"),
        ORANGE("Orange"),
        STRAWBERRY("Strawberry");

        private String id;
        Fruit(String id) {
           this.id = id;
        }

    }
}
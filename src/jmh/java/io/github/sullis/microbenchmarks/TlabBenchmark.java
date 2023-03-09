
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
    public void tlabOneObjectOneThread(final Blackhole bh) {
        bh.consume(new Object());
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    @Threads(1)
    @Fork(jvmArgs = "-XX:-UseTLAB")
    public void noTlabOneObjectOneThread(final Blackhole bh) {
        bh.consume(new Object());
    }
}
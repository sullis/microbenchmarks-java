
package io.github.sullis.microbenchmarks;

import com.google.common.net.InetAddresses;
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
@Threads(1)
public class Ip6AddressBenchmark {
    private static final String V6_IP_ADDRESS = "2607:fb10:2:232:72f3:95ff:fe03:a6e7";

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void guava(final Blackhole bh) {
        bh.consume(InetAddresses.forString(V6_IP_ADDRESS).getAddress().length == 16);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void stringSplit(final Blackhole bh) {
        bh.consume(V6_IP_ADDRESS.split(":").length == 8);
    }
}
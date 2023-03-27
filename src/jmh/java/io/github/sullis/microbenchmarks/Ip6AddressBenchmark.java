
package io.github.sullis.microbenchmarks;

import com.google.common.net.InetAddresses;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Threads(1)
public class Ip6AddressBenchmark {
    private static final Method GUAVA_IP_STRING_TO_BYTES;

    static {
        GUAVA_IP_STRING_TO_BYTES = ReflectionUtils.findMethod(InetAddresses.class, "ipStringToBytes", String.class);
        GUAVA_IP_STRING_TO_BYTES.setAccessible(true);
    }

    private final String ipAddress = "2607:fb10:2:232:72f3:95ff:fe03:a6e7";

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void guava(final Blackhole bh) {
        bh.consume(InetAddresses.forString(ipAddress).getAddress().length == 16);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void guavaPrivateMethod(final Blackhole bh) throws Exception {
        final byte[] result = (byte[]) GUAVA_IP_STRING_TO_BYTES.invoke(null, ipAddress);
        bh.consume(result.length == 16);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void stringSplit(final Blackhole bh) {
        bh.consume(ipAddress.split(":").length == 8);
    }
}
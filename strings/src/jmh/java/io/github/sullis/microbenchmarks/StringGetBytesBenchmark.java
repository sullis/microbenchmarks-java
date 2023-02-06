
package io.github.sullis.microbenchmarks;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class StringGetBytesBenchmark {
    @Param(value = { "0", "10", "1000" })
    private int length;
    private String text;

    @Setup
    public void setup() {
        text = makeString(length);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void getBytes(final Blackhole bh) {
        bh.consume(text.getBytes(StandardCharsets.UTF_8));
    }

    private static String makeString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}

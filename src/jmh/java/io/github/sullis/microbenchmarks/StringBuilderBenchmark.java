
package io.github.sullis.microbenchmarks;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.SplittableRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class StringBuilderBenchmark {
    @Param(value = { "16", "2000" })
    private int stringBuilderInitialCapacity;

    private final int lineLength = 2000;

    private final int segmentSize = 50;

    private String[] segments;

    @Setup
    public void setup() {
        System.out.println("benchmark setup");
        final int numberOfSegments = lineLength / segmentSize;
        System.out.println("numberOfSegments: " + numberOfSegments);
        segments = new String[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = RandomStringUtils.randomAlphabetic(segmentSize);
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void append(final Blackhole bh) {
        System.out.println("benchmark append");
        StringBuilder stringBuilder = new StringBuilder(stringBuilderInitialCapacity);
        for (int i = 0; i < segments.length; i++) {
            System.out.println("capacity: " + stringBuilder.capacity());
            stringBuilder.append(segments[i]);
        }
        bh.consume(stringBuilder);
    }
}
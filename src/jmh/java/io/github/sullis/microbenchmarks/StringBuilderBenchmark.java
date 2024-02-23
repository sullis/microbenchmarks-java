
package io.github.sullis.microbenchmarks;

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
public class StringBuilderBenchmark {
    private final int lineLength = 2000;

    private final int segmentSize = 50;

    private String[] segments;

    @Param
    private StringBuilderSupplier stringBuilderSupplier;

    public enum StringBuilderSupplier {

        INIT_CAPACITY_16() {
            @Override
            public StringBuilder get() {
                return new StringBuilder(16);
            }
        },
        INIT_CAPACITY_2048() {
            @Override
            public StringBuilder get() {
                return new StringBuilder(2048);
            }
        },
        THREAD_LOCAL_INIT_CAPACITY_2048() {
            @Override
            public StringBuilder get() {
                StringBuilder sb = ThreadLocal.withInitial(() -> new StringBuilder(2048)).get();
                sb.setLength(0);
                return sb;
            }

        }
        ;

        public StringBuilder get() {
            return null;
        }
    }
    @Setup
    public void setup() {
        final int numberOfSegments = lineLength / segmentSize;
        segments = new String[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = RandomStringUtils.randomAlphabetic(segmentSize);
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void append(final Blackhole bh) {
        StringBuilder stringBuilder = stringBuilderSupplier.get();
        for (int i = 0; i < segments.length; i++) {
            stringBuilder.append(segments[i]);
        }
        bh.consume(stringBuilder);
    }
}
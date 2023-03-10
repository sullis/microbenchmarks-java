
package io.github.sullis.microbenchmarks;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.logging.log4j.core.util.CachedClock;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.CoarseCachedClock;
import org.apache.logging.log4j.core.util.SystemClock;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class Log4j2ClockBenchmark {
    @Param
    private ClockSupplier clockSupplier;

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void measureClock(final Blackhole bh) {
        final Clock c = this.clockSupplier.get();
        bh.consume(c.currentTimeMillis());
    }

    public enum ClockSupplier {
        SYSTEM_CLOCK(SystemClock::new),
        CACHED_CLOCK(CachedClock::instance),
        COARSE_CACHED_CLOCK(CoarseCachedClock::instance),
        CONSTANT_CLOCK(ConstantClock::new);

        private final Supplier<Clock> supplier;

        ClockSupplier(Supplier<Clock> clockSupplier) {
            this.supplier = clockSupplier;
        }

        public Clock get() {
            return this.supplier.get();
        }
    }

    private static class ConstantClock implements Clock {

        @Override
        public long currentTimeMillis() {
            return 0;
        }
    }
}

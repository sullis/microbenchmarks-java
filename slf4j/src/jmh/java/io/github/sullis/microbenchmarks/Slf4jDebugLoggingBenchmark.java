
package io.github.sullis.microbenchmarks;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@State(Scope.Thread)
public class Slf4jDebugLoggingBenchmark {

    private Logger logger;

    @Setup
    public void setup() throws Exception {
        final String loggerName = "example.jmh."; // + UUID.randomUUID().toString();
        logger = LoggerFactory.getLogger(loggerName);
        System.out.println("logger.isDebugEnabled: " + logger.isDebugEnabled());
        assert(logger.isDebugEnabled());
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void isDebugEnabledOneArg(final Blackhole bh) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Hello world, arg1 {}", "arg1");
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void isDebugEnabledThreeArgs(final Blackhole bh) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Hello world, arg1 {} arg2 {}, arg3 {}", "arg1", "arg2", "arg3");
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void loggerDebugOneArg(final Blackhole bh) throws Exception {
        logger.debug("Hello world, arg1 {}", "arg1");
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void loggerDebugThreeArgs(final Blackhole bh) throws Exception {
        logger.debug("Hello world, arg1 {} arg2 {}, arg3 {}", "arg1", "arg2", "arg3");
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void loggerDebugZeroArgs(final Blackhole bh) throws Exception {
        logger.debug("Hello world");
    }
}

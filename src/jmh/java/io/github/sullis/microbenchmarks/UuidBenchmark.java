
package io.github.sullis.microbenchmarks;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.SplittableRandom;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.netflix.util.concurrent.ConcurrentUUIDFactory;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@Threads(value = Threads.MAX)
public class UuidBenchmark {
    @Param
    private UuidSupplier uuidSupplier;

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void uuidGeneration(final Blackhole bh) {
        final UUID uuid = this.uuidSupplier.get();
        bh.consume(uuid);
    }

    public enum UuidSupplier {
        JAVA_UTIL_UUID(java.util.UUID::randomUUID),
        NETFLIX_CONCURRENT_UUID_FACTORY(new Supplier<UUID>() {
            private static final com.netflix.util.concurrent.ConcurrentUUIDFactory factory = new ConcurrentUUIDFactory();

            @Override
            public UUID get() {
                return factory.generateRandomUuid();
            }
        }),
        FASTERXML_TIME_BASED_GENERATOR(new Supplier<UUID>() {
            private static final TimeBasedGenerator generator = Generators.timeBasedGenerator();
            @Override
            public UUID get() {
                return generator.generate();
            }
        }),
        FASTERXML_RANDOM_BASED_GENERATOR(new Supplier<UUID>() {
            private static final RandomBasedGenerator generator = Generators.randomBasedGenerator();

            @Override
            public UUID get() {
                return generator.generate();
            }
        }),
        DATASTAX_SPLITTABLE_RANDOM_GENERATOR(new Supplier<UUID>() {
            // Note: instances of SplittableRandom are not thread-safe
            private static final ThreadLocal<SplittableRandom> threadSplittableRandom =
                    new ThreadLocal<SplittableRandom>() {
                        @Override protected SplittableRandom initialValue() {
                            return new SplittableRandom();
                        }
                    };

            @Override
            public UUID get() {
                return Uuids.random(threadSplittableRandom.get());
            }
        });

        private final Supplier<UUID> supplier;

        UuidSupplier(Supplier<UUID> uuidSupplier) {
            this.supplier = uuidSupplier;
        }

        public UUID get() {
            return this.supplier.get();
        }
    }

}

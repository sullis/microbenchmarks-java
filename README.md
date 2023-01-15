# microbenchmarks-java

## Benchmark for Java's LongAdder
```
./gradlew clean :longadder:jmh
```

## Benchmark for java.util.Random
```
./gradlew clean :random:jmh
```

## Benchmark for UUID generation
```
./gradlew clean :uuid:jmh
```

## Benchmark for log4j2 clock
```
./gradlew clean :log4j2clock:jmh
```

## Benchmark for Cache (Guava, Caffeine)
```
./gradlew clean :cache:jmh
```

## LongAdder microbenchmark results


```
MacOS 12.6.1
MacBook Pro (16-inch, 2021)
Chip Apple M1 Pro
OpenJDK Runtime Environment Zulu17.34+19-CA (build 17.0.3+7-LTS)

# JMH version: 1.36
# VM version: JDK 17.0.3, OpenJDK 64-Bit Server VM, 17.0.3+7-LTS
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 2 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 32 threads, will synchronize iterations
# Benchmark mode: Throughput, ops/time

Benchmark                                                    (counterSupplier)   Mode  Cnt     Score     Error   Units
LongAdderBenchmark.decrementOnly                                ATOMIC_INTEGER  thrpt   10    13.042 ±   0.536  ops/us
LongAdderBenchmark.decrementOnly                                   ATOMIC_LONG  thrpt   10    12.846 ±   0.172  ops/us
LongAdderBenchmark.decrementOnly                                    LONG_ADDER  thrpt   10  1257.957 ±   2.688  ops/us
LongAdderBenchmark.decrementOnly               CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10  1258.442 ±   2.968  ops/us
LongAdderBenchmark.decrementOnly             CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10  1259.370 ±   1.324  ops/us
LongAdderBenchmark.getIncrementDecrement                        ATOMIC_INTEGER  thrpt   10     6.453 ±   0.100  ops/us
LongAdderBenchmark.getIncrementDecrement                           ATOMIC_LONG  thrpt   10     6.489 ±   0.092  ops/us
LongAdderBenchmark.getIncrementDecrement                            LONG_ADDER  thrpt   10    29.757 ±   0.073  ops/us
LongAdderBenchmark.getIncrementDecrement       CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10   619.561 ±   1.068  ops/us
LongAdderBenchmark.getIncrementDecrement     CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10   620.349 ±   0.497  ops/us
LongAdderBenchmark.getOnly                                      ATOMIC_INTEGER  thrpt   10  5286.029 ±  68.864  ops/us
LongAdderBenchmark.getOnly                                         ATOMIC_LONG  thrpt   10  5263.484 ±  65.581  ops/us
LongAdderBenchmark.getOnly                                          LONG_ADDER  thrpt   10  3405.585 ±  18.088  ops/us
LongAdderBenchmark.getOnly                     CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10  5762.971 ± 155.743  ops/us
LongAdderBenchmark.getOnly                   CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10  5825.892 ±  28.763  ops/us
LongAdderBenchmark.incrementDecrement                           ATOMIC_INTEGER  thrpt   10     6.584 ±   0.097  ops/us
LongAdderBenchmark.incrementDecrement                              ATOMIC_LONG  thrpt   10     6.461 ±   0.134  ops/us
LongAdderBenchmark.incrementDecrement                               LONG_ADDER  thrpt   10   619.795 ±   2.052  ops/us
LongAdderBenchmark.incrementDecrement          CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10   619.618 ±   1.734  ops/us
LongAdderBenchmark.incrementDecrement        CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10   620.444 ±   1.011  ops/us
LongAdderBenchmark.incrementDecrementGet                        ATOMIC_INTEGER  thrpt   10     6.507 ±   0.090  ops/us
LongAdderBenchmark.incrementDecrementGet                           ATOMIC_LONG  thrpt   10     6.412 ±   0.074  ops/us
LongAdderBenchmark.incrementDecrementGet                            LONG_ADDER  thrpt   10    29.544 ±   0.156  ops/us
LongAdderBenchmark.incrementDecrementGet       CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10   617.852 ±   1.478  ops/us
LongAdderBenchmark.incrementDecrementGet     CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10   618.374 ±   0.965  ops/us
LongAdderBenchmark.incrementGetDecrementGet                     ATOMIC_INTEGER  thrpt   10     6.035 ±   0.658  ops/us
LongAdderBenchmark.incrementGetDecrementGet                        ATOMIC_LONG  thrpt   10     6.502 ±   0.054  ops/us
LongAdderBenchmark.incrementGetDecrementGet                         LONG_ADDER  thrpt   10    16.269 ±   0.047  ops/us
LongAdderBenchmark.incrementGetDecrementGet    CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10   614.965 ±   1.106  ops/us
LongAdderBenchmark.incrementGetDecrementGet  CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10   614.511 ±   1.104  ops/us
LongAdderBenchmark.incrementOnly                                ATOMIC_INTEGER  thrpt   10    12.781 ±   0.060  ops/us
LongAdderBenchmark.incrementOnly                                   ATOMIC_LONG  thrpt   10    12.861 ±   0.184  ops/us
LongAdderBenchmark.incrementOnly                                    LONG_ADDER  thrpt   10  1258.790 ±   2.289  ops/us
LongAdderBenchmark.incrementOnly               CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10  1259.093 ±   1.934  ops/us
LongAdderBenchmark.incrementOnly             CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10  1258.051 ±   2.741  ops/us

```


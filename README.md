# microbenchmarks-java

## Benchmark for Java's LongAdder
```
./gradlew clean :longadder:jmh
```

## Benchmark for java.util.Random
```
./gradlew clean :random:jmh
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

Benchmark                                            (counterSupplier)   Mode  Cnt     Score    Error   Units
LongAdderBenchmark.decrementOnly                        ATOMIC_INTEGER  thrpt   10    12.330 ±  1.788  ops/us
LongAdderBenchmark.decrementOnly                           ATOMIC_LONG  thrpt   10    13.066 ±  0.137  ops/us
LongAdderBenchmark.decrementOnly                            LONG_ADDER  thrpt   10  1256.873 ±  2.600  ops/us
LongAdderBenchmark.decrementOnly             CACHED_LONG_ADDER_COUNTER  thrpt   10  1256.171 ±  2.995  ops/us
LongAdderBenchmark.getIncrementDecrement                ATOMIC_INTEGER  thrpt   10     6.401 ±  0.052  ops/us
LongAdderBenchmark.getIncrementDecrement                   ATOMIC_LONG  thrpt   10     6.417 ±  0.065  ops/us
LongAdderBenchmark.getIncrementDecrement                    LONG_ADDER  thrpt   10    29.784 ±  0.045  ops/us
LongAdderBenchmark.getIncrementDecrement     CACHED_LONG_ADDER_COUNTER  thrpt   10   618.536 ±  4.233  ops/us
LongAdderBenchmark.getOnly                              ATOMIC_INTEGER  thrpt   10  5290.816 ± 29.227  ops/us
LongAdderBenchmark.getOnly                                 ATOMIC_LONG  thrpt   10  5296.030 ± 36.797  ops/us
LongAdderBenchmark.getOnly                                  LONG_ADDER  thrpt   10  3397.347 ±  8.901  ops/us
LongAdderBenchmark.getOnly                   CACHED_LONG_ADDER_COUNTER  thrpt   10  5824.319 ± 50.918  ops/us
LongAdderBenchmark.incrementDecrement                   ATOMIC_INTEGER  thrpt   10     6.470 ±  0.027  ops/us
LongAdderBenchmark.incrementDecrement                      ATOMIC_LONG  thrpt   10     6.430 ±  0.042  ops/us
LongAdderBenchmark.incrementDecrement                       LONG_ADDER  thrpt   10   611.541 ± 33.077  ops/us
LongAdderBenchmark.incrementDecrement        CACHED_LONG_ADDER_COUNTER  thrpt   10   620.710 ±  0.559  ops/us
LongAdderBenchmark.incrementDecrementGet                ATOMIC_INTEGER  thrpt   10     6.438 ±  0.120  ops/us
LongAdderBenchmark.incrementDecrementGet                   ATOMIC_LONG  thrpt   10     6.523 ±  0.117  ops/us
LongAdderBenchmark.incrementDecrementGet                    LONG_ADDER  thrpt   10    29.559 ±  0.055  ops/us
LongAdderBenchmark.incrementDecrementGet     CACHED_LONG_ADDER_COUNTER  thrpt   10   618.970 ±  1.295  ops/us
LongAdderBenchmark.incrementGetDecrementGet             ATOMIC_INTEGER  thrpt   10     6.480 ±  0.069  ops/us
LongAdderBenchmark.incrementGetDecrementGet                ATOMIC_LONG  thrpt   10     6.463 ±  0.135  ops/us
LongAdderBenchmark.incrementGetDecrementGet                 LONG_ADDER  thrpt   10    16.174 ±  0.129  ops/us
LongAdderBenchmark.incrementGetDecrementGet  CACHED_LONG_ADDER_COUNTER  thrpt   10   617.429 ±  4.838  ops/us
LongAdderBenchmark.incrementOnly                        ATOMIC_INTEGER  thrpt   10    13.117 ±  0.172  ops/us
LongAdderBenchmark.incrementOnly                           ATOMIC_LONG  thrpt   10    12.783 ±  0.094  ops/us
LongAdderBenchmark.incrementOnly                            LONG_ADDER  thrpt   10  1255.144 ±  2.429  ops/us
LongAdderBenchmark.incrementOnly             CACHED_LONG_ADDER_COUNTER  thrpt   10  1255.235 ±  2.030  ops/us

```


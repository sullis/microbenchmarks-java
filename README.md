# microbenchmarks-java

## Benchmark for Java's LongAdder
```
./gradlew clean :longadder:jmh
```

## Benchmark for java.util.Random
```
./gradlew clean :random:jmh
```

## LongAdderBenchmark results


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

Benchmark                                     (counterSupplier)   Mode  Cnt     Score     Error   Units
LongAdderBenchmark.decrementOnly                 ATOMIC_INTEGER  thrpt   10    14.227 ±   1.960  ops/us
LongAdderBenchmark.decrementOnly                    ATOMIC_LONG  thrpt   10    13.078 ±   0.124  ops/us
LongAdderBenchmark.decrementOnly                     LONG_ADDER  thrpt   10  1255.039 ±   3.720  ops/us
LongAdderBenchmark.decrementOnly          AGRONA_ATOMIC_COUNTER  thrpt   10    13.013 ±   0.201  ops/us
LongAdderBenchmark.getIncrementDecrement         ATOMIC_INTEGER  thrpt   10     6.484 ±   0.118  ops/us
LongAdderBenchmark.getIncrementDecrement            ATOMIC_LONG  thrpt   10     6.552 ±   0.043  ops/us
LongAdderBenchmark.getIncrementDecrement             LONG_ADDER  thrpt   10    29.685 ±   0.584  ops/us
LongAdderBenchmark.getIncrementDecrement  AGRONA_ATOMIC_COUNTER  thrpt   10     6.468 ±   0.073  ops/us
LongAdderBenchmark.getOnly                       ATOMIC_INTEGER  thrpt   10  5129.313 ± 284.660  ops/us
LongAdderBenchmark.getOnly                          ATOMIC_LONG  thrpt   10  4594.209 ± 133.225  ops/us
LongAdderBenchmark.getOnly                           LONG_ADDER  thrpt   10  3043.768 ±  36.700  ops/us
LongAdderBenchmark.getOnly                AGRONA_ATOMIC_COUNTER  thrpt   10  3692.805 ±  72.230  ops/us
LongAdderBenchmark.incrementDecrement            ATOMIC_INTEGER  thrpt   10     6.042 ±   0.627  ops/us
LongAdderBenchmark.incrementDecrement               ATOMIC_LONG  thrpt   10     6.511 ±   0.087  ops/us
LongAdderBenchmark.incrementDecrement                LONG_ADDER  thrpt   10   617.382 ±   2.338  ops/us
LongAdderBenchmark.incrementDecrement     AGRONA_ATOMIC_COUNTER  thrpt   10     6.516 ±   0.174  ops/us
LongAdderBenchmark.incrementDecrementGet         ATOMIC_INTEGER  thrpt   10     6.525 ±   0.170  ops/us
LongAdderBenchmark.incrementDecrementGet            ATOMIC_LONG  thrpt   10     6.499 ±   0.035  ops/us
LongAdderBenchmark.incrementDecrementGet             LONG_ADDER  thrpt   10    29.526 ±   0.102  ops/us
LongAdderBenchmark.incrementDecrementGet  AGRONA_ATOMIC_COUNTER  thrpt   10     6.489 ±   0.151  ops/us
LongAdderBenchmark.incrementOnly                 ATOMIC_INTEGER  thrpt   10    12.979 ±   0.555  ops/us
LongAdderBenchmark.incrementOnly                    ATOMIC_LONG  thrpt   10    14.896 ±   2.274  ops/us
LongAdderBenchmark.incrementOnly                     LONG_ADDER  thrpt   10  1168.442 ±  32.727  ops/us
LongAdderBenchmark.incrementOnly          AGRONA_ATOMIC_COUNTER  thrpt   10    14.865 ±   0.999  ops/us

```


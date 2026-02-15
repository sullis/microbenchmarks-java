# microbenchmarks-java

A collection of JMH (Java Microbenchmark Harness) benchmarks for measuring performance characteristics of various Java operations, libraries, and data structures.

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Available Benchmarks](#available-benchmarks)
- [Usage](#usage)
  - [Run All Benchmarks](#run-all-benchmarks)
  - [Run Specific Benchmark](#run-specific-benchmark)
  - [Benchmark Configuration](#benchmark-configuration)
- [Results](#results)
- [Contributing](#contributing)
- [Resources](#resources)

## Overview

This project contains performance benchmarks using the Java Microbenchmark Harness (JMH) framework. The benchmarks cover a wide range of topics including concurrency primitives, caching, compression, serialization, and more.

## Prerequisites

- Java 21 or later
- Gradle (wrapper included)

## Available Benchmarks

The following benchmarks are available in this project:

| Benchmark | Description |
|-----------|-------------|
| `ArrayListIterationBenchmark` | ArrayList iteration performance |
| `CacheBenchmark` | Cache implementation performance (Caffeine, Guava) |
| `CompressionBenchmark` | Compression algorithm performance (Brotli, Zstd) |
| `CounterBenchmark` | Counter implementations (AtomicInteger, AtomicLong, LongAdder) |
| `EnumValuesBenchmark` | Enum values() method performance |
| `HttpHeadersBenchmark` | HTTP headers parsing and manipulation |
| `Ip6AddressBenchmark` | IPv6 address parsing and validation |
| `JacksonTypeReferenceBenchmark` | Jackson JSON TypeReference performance |
| `Log4j2ClockBenchmark` | Log4j2 clock implementations |
| `NextDoubleBenchmark` | Random double generation performance |
| `OffHeapAllocationBenchmark` | Off-heap memory allocation |
| `RandomBenchmark` | Random number generation |
| `ReactorNettyServerBenchmark` | Reactor Netty server performance |
| `Slf4jDebugLoggingBenchmark` | SLF4J debug logging overhead |
| `StringBuilderBenchmark` | StringBuilder vs String concatenation |
| `StringGetBytesBenchmark` | String to byte array conversion |
| `TlabBenchmark` | Thread-local allocation buffer (TLAB) |
| `UuidBenchmark` | UUID generation algorithms |
| `ZuulResponseSetBodyBenchmark` | Netflix Zuul response body operations |

## Usage

### Run All Benchmarks
```
./gradlew clean jmh
```

### Run Specific Benchmark
```bash
# Run CounterBenchmark
./gradlew clean jmh -Dbenchmark=CounterBenchmark

# Run CacheBenchmark
./gradlew clean jmh -Dbenchmark=CacheBenchmark

# Run CompressionBenchmark
./gradlew clean jmh -Dbenchmark=CompressionBenchmark
```

### Benchmark Configuration

The benchmarks are configured with the following default settings (see `build.gradle.kts`):

- **Fork**: 2 iterations
- **Warmup**: 2 iterations
- **Measurement**: 5 iterations
- **Profilers**: GC profiling enabled
- **JMH Version**: 1.37

You can customize these settings by modifying the `jmh` block in `build.gradle.kts`.

## Results


### Sample Results: CounterBenchmark

<details>
<summary>Click to expand detailed benchmark results</summary>

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

Benchmark                                                    (counterSupplier)   Mode  Cnt     Score    Error   Units
CounterBenchmark.decrementOnly                                ATOMIC_INTEGER  thrpt   10    13.035 ±  0.244  ops/us
CounterBenchmark.decrementOnly                                   ATOMIC_LONG  thrpt   10    13.190 ±  0.181  ops/us
CounterBenchmark.decrementOnly                                    LONG_ADDER  thrpt   10  1245.525 ± 12.299  ops/us
CounterBenchmark.decrementOnly               CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10  1249.400 ±  5.258  ops/us
CounterBenchmark.decrementOnly             CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10  1247.845 ±  9.661  ops/us
CounterBenchmark.getIncrementDecrement                        ATOMIC_INTEGER  thrpt   10     6.390 ±  0.043  ops/us
CounterBenchmark.getIncrementDecrement                           ATOMIC_LONG  thrpt   10     6.522 ±  0.172  ops/us
CounterBenchmark.getIncrementDecrement                            LONG_ADDER  thrpt   10    29.696 ±  0.068  ops/us
CounterBenchmark.getIncrementDecrement       CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10   618.856 ±  1.687  ops/us
CounterBenchmark.getIncrementDecrement     CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10   618.372 ±  1.533  ops/us
CounterBenchmark.getOnly                                      ATOMIC_INTEGER  thrpt   10  5291.998 ± 41.387  ops/us
CounterBenchmark.getOnly                                         ATOMIC_LONG  thrpt   10  5286.500 ± 14.834  ops/us
CounterBenchmark.getOnly                                          LONG_ADDER  thrpt   10  3370.713 ± 26.797  ops/us
CounterBenchmark.getOnly                     CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10  5845.540 ± 65.779  ops/us
CounterBenchmark.getOnly                   CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10  5798.926 ± 41.794  ops/us
CounterBenchmark.incrementDecrement                           ATOMIC_INTEGER  thrpt   10     6.471 ±  0.023  ops/us
CounterBenchmark.incrementDecrement                              ATOMIC_LONG  thrpt   10     6.545 ±  0.057  ops/us
CounterBenchmark.incrementDecrement                               LONG_ADDER  thrpt   10   618.912 ±  1.640  ops/us
CounterBenchmark.incrementDecrement          CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10   617.447 ±  3.121  ops/us
CounterBenchmark.incrementDecrement        CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10   618.295 ±  6.774  ops/us
CounterBenchmark.incrementDecrementGet                        ATOMIC_INTEGER  thrpt   10     6.061 ±  0.816  ops/us
CounterBenchmark.incrementDecrementGet                           ATOMIC_LONG  thrpt   10     6.476 ±  0.086  ops/us
CounterBenchmark.incrementDecrementGet                            LONG_ADDER  thrpt   10    29.588 ±  0.112  ops/us
CounterBenchmark.incrementDecrementGet       CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10   617.597 ±  1.519  ops/us
CounterBenchmark.incrementDecrementGet     CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10   617.999 ±  1.694  ops/us
CounterBenchmark.incrementGetDecrementGet                     ATOMIC_INTEGER  thrpt   10     6.453 ±  0.087  ops/us
CounterBenchmark.incrementGetDecrementGet                        ATOMIC_LONG  thrpt   10     6.438 ±  0.037  ops/us
CounterBenchmark.incrementGetDecrementGet                         LONG_ADDER  thrpt   10    16.235 ±  0.024  ops/us
CounterBenchmark.incrementGetDecrementGet    CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10   617.180 ±  8.163  ops/us
CounterBenchmark.incrementGetDecrementGet  CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10   612.047 ±  2.110  ops/us
CounterBenchmark.incrementOnly                                ATOMIC_INTEGER  thrpt   10    12.062 ±  1.281  ops/us
CounterBenchmark.incrementOnly                                   ATOMIC_LONG  thrpt   10    13.092 ±  0.202  ops/us
CounterBenchmark.incrementOnly                                    LONG_ADDER  thrpt   10  1256.043 ±  3.706  ops/us
CounterBenchmark.incrementOnly               CACHED_LONG_ADDER_COUNTER_10_MS  thrpt   10  1255.447 ±  4.100  ops/us
CounterBenchmark.incrementOnly             CACHED_LONG_ADDER_COUNTER_1000_MS  thrpt   10  1254.679 ±  3.406  ops/us
```

**Key Findings**: LongAdder significantly outperforms AtomicInteger and AtomicLong in high-contention scenarios. For increment-only operations, LongAdder achieves ~104x better throughput than AtomicInteger (1256 ops/us vs 12 ops/us) and ~96x better than AtomicLong (1256 ops/us vs 13 ops/us).

</details>

## Contributing

Contributions are welcome! To add a new benchmark:

1. Create a new Java class in `src/jmh/java/io/github/sullis/microbenchmarks/`
2. Annotate your class with JMH annotations (`@Benchmark`, `@State`, etc.)
3. Follow the existing benchmark patterns
4. Run your benchmark locally to verify it works
5. Submit a pull request

## Resources

- [JMH - Java Microbenchmark Harness](https://github.com/openjdk/jmh) - Official JMH documentation
- [How difficult can it be to write efficient code?](https://www.youtube.com/watch?v=sNqbGU-8ys8) - Roberto Cortez
- [JMH Samples](https://github.com/openjdk/jmh/tree/master/jmh-samples/src/main/java/org/openjdk/jmh/samples) - Official JMH examples

## License

See [LICENSE](LICENSE) file for details.

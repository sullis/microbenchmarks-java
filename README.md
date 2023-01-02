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

Benchmark                                 (counterSupplier)   Mode  Cnt     Score    Error   Units
LongAdderBenchmark.decrementOnly             ATOMIC_INTEGER  thrpt   12    13.064 ±  0.205  ops/us
LongAdderBenchmark.decrementOnly                ATOMIC_LONG  thrpt   12    12.888 ±  0.149  ops/us
LongAdderBenchmark.decrementOnly                 LONG_ADDER  thrpt   12  1255.410 ±  2.713  ops/us
LongAdderBenchmark.getOnly                   ATOMIC_INTEGER  thrpt   12  5300.214 ± 53.535  ops/us
LongAdderBenchmark.getOnly                      ATOMIC_LONG  thrpt   12  5291.040 ± 17.456  ops/us
LongAdderBenchmark.getOnly                       LONG_ADDER  thrpt   12  3404.801 ±  9.648  ops/us
LongAdderBenchmark.incrementDecrement        ATOMIC_INTEGER  thrpt   12     6.084 ±  0.651  ops/us
LongAdderBenchmark.incrementDecrement           ATOMIC_LONG  thrpt   12     6.370 ±  0.038  ops/us
LongAdderBenchmark.incrementDecrement            LONG_ADDER  thrpt   12   620.447 ±  0.978  ops/us
LongAdderBenchmark.incrementDecrementGet     ATOMIC_INTEGER  thrpt   12     6.450 ±  0.105  ops/us
LongAdderBenchmark.incrementDecrementGet        ATOMIC_LONG  thrpt   12     6.365 ±  0.008  ops/us
LongAdderBenchmark.incrementDecrementGet         LONG_ADDER  thrpt   12    29.586 ±  0.112  ops/us
LongAdderBenchmark.incrementOnly             ATOMIC_INTEGER  thrpt   12    12.127 ±  1.157  ops/us
LongAdderBenchmark.incrementOnly                ATOMIC_LONG  thrpt   12    12.945 ±  0.014  ops/us
LongAdderBenchmark.incrementOnly                 LONG_ADDER  thrpt   12  1255.494 ±  2.505  ops/us

```


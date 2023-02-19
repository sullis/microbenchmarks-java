
package io.github.sullis.microbenchmarks;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
public class ArrayListIterationBenchmark {
    private ArrayList<String> arrayListOfString;
    private String[] arrayOfString;
    @Param(value = { "10" })
    private int collectionSize;

    @Setup
    public void setup() {
        arrayListOfString = new ArrayList<String>(collectionSize);
        arrayOfString = new String[collectionSize];
        for (int i = 0; i < collectionSize; i++) {
            String s = "s" + i;
            arrayOfString[i] = s;
            arrayListOfString.add(s);
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void iterateOverArrayListUsingIndex(final Blackhole bh) {
        final int size = arrayListOfString.size();
        for (int i = 0; i < size; i++) {
            bh.consume(arrayListOfString.get(i));
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void iterateOverArrayListUsingForSyntax(final Blackhole bh) {
        for (String s: arrayOfString) {
            bh.consume(s);
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void iterateOverArrayUsingIndex(final Blackhole bh) {
        final int size = arrayOfString.length;
        for (int i = 0; i < size; i++) {
            bh.consume(arrayOfString[i]);
        }
    }
}

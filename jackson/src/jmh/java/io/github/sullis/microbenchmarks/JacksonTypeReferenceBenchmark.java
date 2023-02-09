
package io.github.sullis.microbenchmarks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class JacksonTypeReferenceBenchmark {
    private static final TypeReference<Map<Integer, String>> STATIC_TYPE_REF = new TypeReference<Map<Integer, String>>() {};

    @Param(value = { "0", "10", "100" })
    private int mapSize;
    private String json;
    private ObjectMapper mapper;

    @Setup
    public void setup() throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < mapSize; i++) {
            map.put(Integer.valueOf(i), "foobar");
        }
        mapper = new ObjectMapper();
        json = mapper.writeValueAsString(map);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void staticTypeRef(final Blackhole bh) throws Exception {
        bh.consume(mapper.readValue(json, STATIC_TYPE_REF));
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void typeRef(final Blackhole bh) throws Exception {
        bh.consume(mapper.readValue(json, new TypeReference<Map<Integer, String>>() {}));
    }

}

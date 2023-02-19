
package io.github.sullis.microbenchmarks;

import com.netflix.zuul.message.Headers;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http2.DefaultHttp2Headers;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class HttpHeadersBenchmark {

    @Param(value = { "1" })
    private int numHeaders;
    private String[] headerNames;
    private String[] headerValues;

    @Setup
    public void setup() {
        headerNames = new String[numHeaders];
        headerValues = new String[numHeaders];
        for (int i = 0; i < numHeaders; i++) {
            headerNames[i] = "name" + i;
            headerValues[i] = "value" + i;
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void zuulHttpHeaders(final Blackhole bh) {
        Headers headers = new Headers();
        headers.add("a", "b");
        bh.consume(headers.contains("a"));
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void nettyHttpHeaders(final Blackhole bh) {
        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("a", "b");
        bh.consume(headers.contains("a"));
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void nettyHttp2Headers(final Blackhole bh) {
        DefaultHttp2Headers headers = new DefaultHttp2Headers();
        headers.add("a", "b");
        bh.consume(headers.contains("a"));
    }
}

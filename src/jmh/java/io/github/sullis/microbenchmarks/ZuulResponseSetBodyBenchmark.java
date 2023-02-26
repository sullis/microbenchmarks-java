
package io.github.sullis.microbenchmarks;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import com.netflix.zuul.context.SessionContext;
import com.netflix.zuul.message.Headers;
import com.netflix.zuul.message.http.HttpQueryParams;
import com.netflix.zuul.message.http.HttpRequestMessage;
import com.netflix.zuul.message.http.HttpRequestMessageImpl;
import com.netflix.zuul.message.http.HttpResponseMessageImpl;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import com.netflix.zuul.message.http.HttpResponseMessage;
import org.apache.commons.lang3.RandomStringUtils;

@State(Scope.Thread)
public class ZuulResponseSetBodyBenchmark {

    private HttpResponseMessage responseMessage;
    private String body;
    private byte[] bodyByteArray;
    @Param(value = { "0", "100", "1000", "10000", "100000"})
    private int bodySize;

    @Setup
    public void setup() {
        body = RandomStringUtils.randomAlphabetic(bodySize);
        bodyByteArray = body.getBytes(StandardCharsets.UTF_8);
        SessionContext sessionContext = new SessionContext();
        HttpRequestMessage request = new HttpRequestMessageImpl(sessionContext, "http", "GET", "/", new HttpQueryParams(), new Headers(), "127.0.0.1", "http", 8080, "servername");
        responseMessage = new HttpResponseMessageImpl(sessionContext, request, 200);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void setBodyByteArray(final Blackhole bh) {
        responseMessage.setBody(bodyByteArray);
        bh.consume(responseMessage);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void setBodyString(final Blackhole bh) {
        responseMessage.setBodyAsText(body);
        bh.consume(responseMessage);
    }
}

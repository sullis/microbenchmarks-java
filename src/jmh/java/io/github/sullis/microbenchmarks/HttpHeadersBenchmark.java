
package io.github.sullis.microbenchmarks;

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
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class HttpHeadersBenchmark {

    @Param(value = { "1" })
    private int numHeaders;
    @Param
    private HttpHeadersType httpHeadersType;

    private HttpHeaderOps httpHeaders;

    private String[] headerNames;
    private String[] headerValues;

    @Setup
    public void setup() throws Exception {
        headerNames = new String[numHeaders];
        headerValues = new String[numHeaders];
        for (int i = 0; i < numHeaders; i++) {
            headerNames[i] = "name" + i;
            headerValues[i] = "value" + i;
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Threads(1)
    @Benchmark
    public void addHeaders(final Blackhole bh) throws Exception {
        httpHeaders = httpHeadersType.newInstance();
        httpHeaders.addHeader(headerNames[0], headerValues[0]);
        bh.consume(httpHeaders);
    }

    public enum HttpHeadersType {
        NETTY_HTTP1(NettyHttp1.class),
        NETTY_HTTP2(NettyHttp2.class),
        SPRINGWEB(SpringHttp.class),
        ZUUL(ZuulHttp.class);

        private final Class<? extends HttpHeaderOps> clazz;

        HttpHeadersType(Class<? extends HttpHeaderOps> clazz) {
           this.clazz = clazz;
        }

        public HttpHeaderOps newInstance() throws Exception {
            return this.clazz.newInstance();
        }
    }

    interface HttpHeaderOps {
        public void addHeader(String name, String value);
        public String getFirst(String name);
    }

    static class NettyHttp1 implements HttpHeaderOps {
        private final DefaultHttpHeaders headers = new DefaultHttpHeaders();

        @Override
        public void addHeader(String name, String value) {
            headers.add(name, value);
        }

        @Override
        public String getFirst(String name) {
           return headers.get(name);
        }
    }

   static class NettyHttp2 implements HttpHeaderOps {
       private final DefaultHttp2Headers headers = new DefaultHttp2Headers();

        @Override
        public void addHeader(String name, String value) {
            headers.add(name, value);
        }

        @Override
        public String getFirst(String name) {
            CharSequence value = headers.get(name);
            return (value == null) ? null : value.toString();
        }
    }

    static class ZuulHttp implements HttpHeaderOps {
        private final com.netflix.zuul.message.Headers headers = new com.netflix.zuul.message.Headers();

        @Override
        public void addHeader(String name, String value) {
            headers.add(name, value);
        }

        @Override
        public String getFirst(String name) {
            return headers.getFirst(name);
        }
    }

    static class SpringHttp implements HttpHeaderOps {
        private final org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

        @Override
        public void addHeader(String name, String value) {
            headers.add(name, value);
        }

        @Override
        public String getFirst(String name) {
            return headers.getFirst(name);
        }
    }
}
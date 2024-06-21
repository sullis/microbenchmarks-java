package io.github.sullis.microbenchmarks;

import io.netty.channel.EventLoopGroup;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.resources.LoopResources;


@State(Scope.Thread)
public class ReactorNettyServerBenchmark {
    private DisposableServer server;
    private HttpClient httpClient;
    private HttpRequest httpGetRequest;

    @Param(value = { "true", "false" })
    private boolean preferNativeTransport;

    @Setup
    public void setup() throws Exception {
        LoopResources loopResources = LoopResources.create("test");

        server =
            HttpServer.create()
                .accessLog(false)
                .compress(false)
                .runOn(loopResources, preferNativeTransport)
                .bindNow();

        server.onDispose().block();
        httpClient = HttpClient.newHttpClient();
        final URI uri = URI.create("http://localhost:" + server.port() + "/");
        httpGetRequest = HttpRequest.newBuilder().GET().uri(uri).build();
    }

    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void httpGet() throws Exception {
        httpClient.send(httpGetRequest, HttpResponse.BodyHandlers.discarding());
    }
}

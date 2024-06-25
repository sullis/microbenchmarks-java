package io.github.sullis.microbenchmarks;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.resources.LoopResources;


@State(Scope.Thread)
public class ReactorNettyServerBenchmark {
    private static final String RESPONSE_BODY = "a".repeat(250_000);
    private DisposableServer server;
    private HttpClient httpClient;
    private HttpRequest httpGetRequest;

    @Param(value = { "true", "false" })
    private boolean preferNativeTransport;

    @Setup
    public void setup() throws Exception {
        LoopResources loopResources = LoopResources.create("test");

        HttpServer httpServer =
            HttpServer.create()
                .handle((request, response) -> response.sendString(Mono.just(RESPONSE_BODY)))
                .accessLog(false)
                .compress(false)
                .runOn(loopResources, preferNativeTransport);

        server = httpServer.bindNow();

        System.out.println("ReactorNetty HttpServer port: " + server.port());

        httpClient = HttpClient.newHttpClient();
        final URI uri = URI.create("http://localhost:" + server.port() + "/");
        httpGetRequest = HttpRequest.newBuilder().GET().uri(uri)
            .header("aaa", "a-value")
            .header("bbb", "b-value")
            .header("ccc", "c-value")
            .timeout(Duration.ofMillis(1000))
            .build();
    }

    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void httpGet() throws Exception {
        httpClient.send(httpGetRequest, HttpResponse.BodyHandlers.discarding());
    }
}

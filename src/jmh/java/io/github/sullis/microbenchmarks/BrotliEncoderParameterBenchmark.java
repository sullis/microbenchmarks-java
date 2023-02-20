
package io.github.sullis.microbenchmarks;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.aayushatharva.brotli4j.encoder.BrotliOutputStream;
import com.aayushatharva.brotli4j.encoder.Encoder;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class BrotliEncoderParameterBenchmark {

    @Param(value = {  "1", "4", "11" })
    private int quality;
    @Param(value = { "10000" })
    private int size;
    private String text;
    private byte[] textBytes;
    private Encoder.Parameters encoderParameters;

    @Setup
    public void setup() {
        Brotli4jLoader.ensureAvailability();
        text = RandomStringUtils.random(size, "ab");
        textBytes = text.getBytes(StandardCharsets.UTF_8);
        encoderParameters = new Encoder.Parameters();
        encoderParameters.setMode(Encoder.Mode.TEXT);
        encoderParameters.setQuality(quality);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public void compress(final Blackhole bh) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        BrotliOutputStream brotli = new BrotliOutputStream(baos, encoderParameters);
        brotli.write(textBytes);
        brotli.flush();
        brotli.close();
        baos.close();
        bh.consume(brotli);
    }

}

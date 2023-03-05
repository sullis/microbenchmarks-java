
package io.github.sullis.microbenchmarks;

import com.aayushatharva.brotli4j.Brotli4jLoader;
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
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.zip.GZIPOutputStream;
import com.aayushatharva.brotli4j.encoder.BrotliOutputStream;

@State(Scope.Thread)
public class CompressionBenchmark {

    @Param
    private CompressionType compressionType;
    @Param(value = { "100000" })
    private int size;
    private String text;
    private byte[] textBytes;

    @Setup
    public void setup() {
        Brotli4jLoader.ensureAvailability();
        text = RandomStringUtils.random(size, "ab");
        textBytes = text.getBytes(StandardCharsets.UTF_8);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void compress(final Blackhole bh) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        CompressionOps ops = this.compressionType.supplier.get();
        ops.compress(textBytes, baos);
        bh.consume(baos);
        bh.consume(ops);
    }

    public enum CompressionType {

        GZIP(new Supplier<CompressionOps>() {
            @Override
            public CompressionOps get() {
                return new CompressionOps() {

                    @Override
                    public void compress(byte[] data, OutputStream out) throws IOException {
                        GZIPOutputStream gzip = new GZIPOutputStream(out);
                        gzip.write(data);
                        gzip.flush();
                        gzip.close();
                    }
                };
            }
        }),
        BROTLI_0(Brotli4jHelper.brotli(0)),
        BROTLI_4(Brotli4jHelper.brotli(4)),
        BROTLI_11(Brotli4jHelper.brotli(11));

        private final Supplier<CompressionOps> supplier;

        CompressionType(Supplier<CompressionOps> supplier) {
            this.supplier = supplier;
        }

        public Supplier<CompressionOps> getSupplier() {
            return this.supplier;
        };

    }

    public interface CompressionOps {
        void compress(byte[] data, OutputStream out) throws IOException;
    }



    static class Brotli4jHelper {
       public static Supplier<CompressionOps> brotli(final int quality) {
           final Encoder.Parameters params = new Encoder.Parameters();
           params.setMode(Encoder.Mode.TEXT);
           params.setQuality(quality);
           return new Supplier<CompressionOps>() {
               @Override
               public CompressionOps get() {
                   return new CompressionOps() {

                       @Override
                       public void compress(final byte[] data, final OutputStream out) throws IOException {
                           Brotli4jHelper.compress(params, data, out);
                       }
                   };
               }
           };
       }

        public static void compress(final Encoder.Parameters encoderParameters,
                                    final byte[] data,
                                    final OutputStream out) throws IOException {
            BrotliOutputStream brotli = new BrotliOutputStream(out, encoderParameters);
            brotli.write(data);
            brotli.flush();
            brotli.close();
        }
    }
}
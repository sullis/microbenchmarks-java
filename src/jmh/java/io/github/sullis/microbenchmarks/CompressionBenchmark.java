
package io.github.sullis.microbenchmarks;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.aayushatharva.brotli4j.encoder.Encoder;
import com.github.luben.zstd.Zstd;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.AuxCounters;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.zip.GZIPOutputStream;
import com.aayushatharva.brotli4j.encoder.BrotliOutputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@State(Scope.Thread)
@Threads(1)
public class CompressionBenchmark {
    private static final ConcurrentHashMap<String, byte[]> DATA = new ConcurrentHashMap<>();
    @Param
    private CompressionType compressionType;
    @Param(value = { "100000.txt" })
    private String filename;
    private byte[] textBytes;

    @AuxCounters(AuxCounters.Type.EVENTS)
    @State(value=Scope.Thread)
    public static class CompressionInfo {
        public long uncompressedByteCount = 0;
        public long compressedByteCount = 0;

        void update(final long uncompressedBytes, final long compressedBytes) {
            uncompressedByteCount += uncompressedBytes;
            compressedByteCount += compressedBytes;
        }

        private double compressionPercentage() {
          assert(uncompressedByteCount > compressedByteCount);
          double diff = uncompressedByteCount - compressedByteCount;
          assert(diff >= 0);
          final double result = Math.round((100 * (diff / uncompressedByteCount)));
          return result;
        }
    }

    @Setup
    public void beforeBenchmark() throws Exception {
        Brotli4jLoader.ensureAvailability();
        textBytes = loadData(filename);
    }

    static private byte[] loadData(final String filename) {
        return DATA.computeIfAbsent(filename, (k) -> {
            URL fileUrl = Resources.getResource(filename);
            assertNotNull(fileUrl);
            try {
                return Resources.toByteArray(fileUrl);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Benchmark
    public void compress(final Blackhole bh, final CompressionInfo compressionInfo) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(textBytes.length);
        CompressionOps ops = this.compressionType.supplier.get();
        ops.compress(textBytes, baos);
        compressionInfo.update(this.textBytes.length, baos.size());
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
        BROTLI_11(Brotli4jHelper.brotli(11)),
        ZSTD_NEGATIVE_7(ZstdHelper.zstd(-7)),
        ZSTD_ZERO(ZstdHelper.zstd(0)),
        ZSTD_SEVEN(ZstdHelper.zstd(7));

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

    static class ZstdHelper {
        public static Supplier<CompressionOps> zstd(final int compressionLevel) {
            return new Supplier<CompressionOps>() {
                @Override
                public CompressionOps get() {
                    return new CompressionOps() {

                        @Override
                        public void compress(final byte[] data, final OutputStream out) throws IOException {
                            Zstd.compress(data, compressionLevel);
                        }
                    };
                }
            };
        }
    }
}

class DataFileHelper {

    public static void writeFile(final int size) throws Exception {
        File file = new File("" + size + ".txt");
        String data = RandomStringUtils.random(size, "ab");
        Files.write(data.getBytes(StandardCharsets.UTF_8), file);
    }

    public static void main(String[] args) throws Exception {
        writeFile(10000);
        writeFile(100000);
    }
}

package io.github.sullis.microbenchmarks;

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
@Threads(1)
public class OffHeapAllocationBenchmark {
    @Param(value = "16")
    private int bufferSize;

    @Param
    private OffHeapAllocationType allocationType;
    private OffHeapAllocationOps allocationOps;

    @Setup
    public void setup() throws Exception {
        allocationOps = allocationType.newInstance();
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void measure(final Blackhole bh) {
        final Object buffer = allocationOps.allocateBuffer(bufferSize);
        allocationOps.releaseBuffer(buffer);
        bh.consume(buffer);
    }

    public enum OffHeapAllocationType {
        NETTY4(Netty4Allocation.class),
        NETTY5(Netty5Allocation.class);

        private final Class<? extends OffHeapAllocationOps> clazz;

        OffHeapAllocationType(Class<? extends OffHeapAllocationOps> clazz) {
            this.clazz = clazz;
        }

        public OffHeapAllocationOps newInstance() throws Exception {
            return clazz.getDeclaredConstructor().newInstance();
        }
    }

    interface OffHeapAllocationOps {
        Object allocateBuffer(int size);
        void releaseBuffer(Object obj);
    }

    static public class Netty4Allocation implements OffHeapAllocationOps {
        private final io.netty.buffer.ByteBufAllocator alloc = io.netty.buffer.PooledByteBufAllocator.DEFAULT;

        public Netty4Allocation() { }

        @Override
        public Object allocateBuffer(int size) {
            return alloc.directBuffer(size);
        }

        @Override
        public void releaseBuffer(Object obj) {
            io.netty.util.ReferenceCountUtil.release(obj);
        }
    }

    static public class Netty5Allocation implements OffHeapAllocationOps {
        private final io.netty5.buffer.BufferAllocator alloc = io.netty5.buffer.DefaultBufferAllocators.offHeapAllocator();

        public Netty5Allocation() { }

        @Override
        public Object allocateBuffer(int size) {
            return alloc.allocate(size);
        }

        @Override
        public void releaseBuffer(Object obj) {
            io.netty5.util.ReferenceCountUtil.release(obj);
        }
    }
}
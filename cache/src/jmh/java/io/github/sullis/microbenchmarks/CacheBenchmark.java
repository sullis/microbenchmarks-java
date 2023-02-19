
package io.github.sullis.microbenchmarks;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
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
public class CacheBenchmark {
    @Param
    private CacheType cacheType;

    @Param(value = {"10", "100"})
    private int maxCapacity;

    private Cache<String, String> cache;

    @Setup
    public void setup() throws Exception {
        cache = this.cacheType.newInstance(maxCapacity);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void putGet(final Blackhole bh) {
        final long now = System.currentTimeMillis();
        final String key = "key" + now;
        final String value = "value" + now;
        cache.put(key, value);
        String valueFromCache = cache.get(key);
        bh.consume(valueFromCache);
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    public void putOnly(final Blackhole bh) {
        final long now = System.currentTimeMillis();
        final String key = "key" + now;
        final String value = "value" + now;
        cache.put(key, value);
        bh.consume(cache);
    }

    public enum CacheType {
        CAFFEINE_CACHE(CaffeineCache.class),
        GUAVA_CACHE(GuavaCache.class);

        public final Class<? extends Cache<String, String>> clazz;

        CacheType(Class<? extends Cache> clazz) {
            this.clazz = (Class<? extends Cache<String, String>>) clazz;
        }

        public Cache<String, String> newInstance(final int maxCapacity) throws Exception {
            Constructor<? extends Cache<String, String>> constructor = this.clazz.getDeclaredConstructor(Integer.TYPE);
            return constructor.newInstance(maxCapacity);
        }
    }

    public static interface Cache<K, V> {
      void put(K key, V value);
      V get(K key);
    }

    public static class CaffeineCache<K, V> implements Cache<K, V> {
        private final com.github.benmanes.caffeine.cache.Cache<K, V> cache;

        public CaffeineCache(int maxSize) {
            cache = Caffeine.newBuilder()
                    .initialCapacity(maxSize)
                    .maximumSize(maxSize)
                    .build();
        }

        @Override
        public void put(K key, V value) {
            cache.put(key, value);
        }

        @Override
        public V get(K key) {
            return cache.getIfPresent(key);
        }

    }

    public static class GuavaCache<K, V> implements Cache<K, V> {
        private final com.google.common.cache.Cache<K, V> cache;

        public GuavaCache(final int maxSize) {
            cache = CacheBuilder.newBuilder()
                        .initialCapacity(maxSize)
                        .maximumSize(maxSize)
                        .build();
        }

        @Override
        public void put(K key, V value) {
            cache.put(key, value);
        }

        @Override
        public V get(K key) {
            return cache.getIfPresent(key);
        }
    }
}

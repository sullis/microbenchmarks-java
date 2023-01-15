
package io.github.sullis.microbenchmarks;

import com.google.common.cache.CacheBuilder;

public class GuavaCache<K, V> implements Cache<K, V> {
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

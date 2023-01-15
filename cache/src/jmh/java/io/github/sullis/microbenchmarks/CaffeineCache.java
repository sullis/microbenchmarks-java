package io.github.sullis.microbenchmarks;

import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineCache<K, V> implements Cache<K, V> {
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


package io.github.sullis.microbenchmarks;

public interface Cache<K, V> {
  void put(K key, V value);
  V get(K key);
}

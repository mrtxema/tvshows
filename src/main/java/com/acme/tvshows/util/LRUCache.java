package com.acme.tvshows.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K,V> {
    private final LinkedHashMap<K,TimestampedValue<V>> cache;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCache(long expirationTimeInMillis) {
        this.cache = new ExpirableLinkedHashMap<>(expirationTimeInMillis);
    }

    public V get(K key) {
        lock.readLock().lock();
        try {
            TimestampedValue<V> timestampedValue = cache.get(key);
            return timestampedValue == null ? null : timestampedValue.getValue();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            cache.put(key, new TimestampedValue<>(value));
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static final class ExpirableLinkedHashMap<K,T extends TimestampedValue<?>> extends LinkedHashMap<K,T> {
        private final long expirationTimeInMillis;

        public ExpirableLinkedHashMap(long expirationTimeInMillis) {
            this.expirationTimeInMillis = expirationTimeInMillis;
        }

        protected boolean removeEldestEntry(Map.Entry<K,T> eldest) {
            return System.currentTimeMillis() - eldest.getValue().getTimestamp() > expirationTimeInMillis;
        }
    }

    private static final class TimestampedValue<V> {
        private final V value;
        private final long timestamp;

        private TimestampedValue(V value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }

        V getValue() {
            return value;
        }

        long getTimestamp() {
            return timestamp;
        }
    }
}

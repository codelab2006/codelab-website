package top.codelab.markdown;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class Cache<K, V> {

    private final Map<K, V> cache;
    private final ReadWriteLock rwLock;

    public Cache() {
        this.cache = new HashMap<>();
        this.rwLock = new ReentrantReadWriteLock();
    }

    public V add(K key, V value) {
        return this.doWithWriteLock(() -> this.cache.put(key, value));
    }

    public V get(K key) {
        return this.doWithReadLock(() -> this.cache.get(key));
    }

    V remove(K key) {
        return this.doWithWriteLock(() -> this.cache.remove(key));
    }

    public Cache<K, V> clear() {
        return this.doWithWriteLock(() -> {
            this.cache.clear();
            return this;
        });
    }

    private V doWithReadLock(Supplier<V> supplier) {
        return this.doWithLock(this.rwLock.readLock(), supplier);
    }

    private <T> T doWithWriteLock(Supplier<T> supplier) {
        return this.doWithLock(this.rwLock.writeLock(), supplier);
    }

    private <T> T doWithLock(final Lock lock, final Supplier<T> supplier) {
        lock.lock();
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }
}

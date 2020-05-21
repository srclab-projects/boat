package xyz.srclab.common.cache;

import com.google.common.collect.MapMaker;
import xyz.srclab.annotation.Immutable;
import xyz.srclab.annotation.Nullable;
import xyz.srclab.common.base.Checker;
import xyz.srclab.common.base.Defaults;
import xyz.srclab.common.collection.MapHelper;

import java.time.Duration;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Cache<K, V> {

    static <K, V> Cache<K, V> newPermanent() {
        return newPermanent(Defaults.CONCURRENCY_LEVEL);
    }

    static <K, V> Cache<K, V> newPermanent(int concurrencyLevel) {
        Map<K, Object> map = concurrencyLevel <= 1 ?
                new HashMap<>() : new MapMaker().concurrencyLevel(concurrencyLevel).makeMap();
        return new MapCache<>(map);
    }

    static <K, V> Cache<K, V> newMapped(Map<K, Object> map) {
        return new MapCache<>(map);
    }

    static <K, V> Cache<K, V> newGcThreadLocal() {
        return new ThreadLocalCache<>(newMapped(CacheSupport.newGcMap(0)));
    }

    static <K, V> Cache<K, V> newGcConcurrent() {
        return newGcConcurrent(Defaults.CONCURRENCY_LEVEL);
    }

    static <K, V> Cache<K, V> newGcConcurrent(int concurrencyLevel) {
        return new MapCache<>(CacheSupport.newGcMap(concurrencyLevel));
    }

    static <K, V> Cache<K, V> newL2Cache(Cache<K, V> l1, Cache<K, V> l2) {
        return new L2Cache<>(l1, l2);
    }

    static <K, V> Cache<K, V> newGcThreadLocalL2() {
        return newGcThreadLocalL2(Defaults.CONCURRENCY_LEVEL);
    }

    static <K, V> Cache<K, V> newGcThreadLocalL2(int concurrencyLevel) {
        Cache<K, V> l1 = newGcConcurrent(concurrencyLevel);
        return newGcThreadLocalL2(l1);
    }

    static <K, V> Cache<K, V> newGcThreadLocalL2(Cache<K, V> l1) {
        Cache<K, V> l2 = newGcThreadLocal();
        return newL2Cache(l1, l2);
    }

    static <K, V> CacheBuilder<K, V> newBuilder() {
        return new CacheBuilder<>();
    }

    boolean contains(K key);

    default boolean containsAll(Iterable<? extends K> keys) {
        for (K key : keys) {
            if (!contains(key)) {
                return false;
            }
        }
        return true;
    }

    default boolean containsAny(Iterable<? extends K> keys) {
        for (K key : keys) {
            if (contains(key)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    V get(K key) throws NoSuchElementException;

    @Nullable
    V get(K key, @Nullable V defaultValue);

    @Nullable
    V get(K key, Function<? super K, ? extends @Nullable V> ifAbsent);

    @Nullable
    V get(K key, CacheFunction<? super K, ? extends @Nullable V> ifAbsent);

    @Nullable
    V compute(K key, BiFunction<? super K, ? super @Nullable V, ? extends @Nullable V> remappingFunction);

    @Immutable
    default Map<K, @Nullable V> getAll(Iterable<? extends K> keys) throws NoSuchElementException {
        Map<K, V> map = new LinkedHashMap<>();
        for (K key : keys) {
            map.put(key, get(key));
        }
        return MapHelper.immutable(map);
    }

    @Immutable
    default Map<K, @Nullable V> getAll(
            Iterable<? extends K> keys, Function<? super K, ? extends @Nullable V> ifAbsent) {
        Map<K, V> map = new LinkedHashMap<>();
        for (K key : keys) {
            map.put(key, get(key, ifAbsent));
        }
        return MapHelper.immutable(map);
    }

    @Immutable
    default Map<K, @Nullable V> getAll(
            Iterable<? extends K> keys, CacheFunction<? super K, ? extends @Nullable V> ifAbsent) {
        Map<K, V> map = new LinkedHashMap<>();
        for (K key : keys) {
            map.put(key, get(key, ifAbsent));
        }
        return MapHelper.immutable(map);
    }

    @Immutable
    default Map<K, @Nullable V> getPresent(Iterable<? extends K> keys) {
        Map<K, V> map = new LinkedHashMap<>();
        Cache<K, Object> cast = (Cache<K, Object>) this;
        Object defaultValue = new Object();
        for (K key : keys) {
            @Nullable Object value = cast.get(key, defaultValue);
            if (value == defaultValue) {
                continue;
            }
            @Nullable V v = value == null ? null : (V) value;
            map.put(key, v);
        }
        return MapHelper.immutable(map);
    }

    default V getNonNull(K key) throws NoSuchElementException, NullPointerException {
        @Nullable V result = get(key);
        Checker.checkNull(result != null);
        return result;
    }

    default V getNonNull(
            K key, Function<? super K, ? extends @Nullable V> ifAbsent) throws NullPointerException {
        @Nullable V result = get(key, ifAbsent);
        Checker.checkNull(result != null);
        return result;
    }

    default V getNonNull(
            K key, CacheFunction<? super K, ? extends @Nullable V> ifAbsent) throws NullPointerException {
        @Nullable V result = get(key, ifAbsent);
        Checker.checkNull(result != null);
        return result;
    }

    void put(K key, @Nullable V value);

    void put(K key, @Nullable V value, CacheExpiry expiry);

    default void putAll(Map<K, ? extends @Nullable V> data) {
        data.forEach(this::put);
    }

    default void putAll(
            Iterable<? extends K> keys, Function<? super K, ? extends @Nullable V> valueFunction) {
        for (K key : keys) {
            @Nullable V value = valueFunction.apply(key);
            put(key, value);
        }
    }

    default void putAll(
            Iterable<? extends K> keys, CacheFunction<? super K, ? extends @Nullable V> valueFunction) {
        for (K key : keys) {
            Cached<? extends V> cached = valueFunction.apply(key);
            @Nullable CacheExpiry expiry = cached.getExpiry();
            if (expiry == null) {
                put(key, cached.getValue());
            } else {
                put(key, cached.getValue(), expiry);
            }
        }
    }

    default void expire(K key, long seconds) {
        expire(key, Duration.ofSeconds(seconds));
    }

    void expire(K key, Duration duration);

    void expire(K key, Function<? super K, Duration> durationFunction);

    default void expireAll(Iterable<? extends K> keys, long seconds) {
        expireAll(keys, Duration.ofSeconds(seconds));
    }

    default void expireAll(Iterable<? extends K> keys, Duration duration) {
        expireAll(keys, k -> duration);
    }

    default void expireAll(Iterable<? extends K> keys, Function<? super K, Duration> durationFunction) {
        keys.forEach(k -> expire(k, durationFunction));
    }

    void remove(K key);

    default void removeAll(Iterable<? extends K> keys) {
        for (K key : keys) {
            remove(key);
        }
    }

    void removeAll();
}

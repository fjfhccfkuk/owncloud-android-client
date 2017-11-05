package com.example.hsx.data.cache;
import android.util.LruCache;

/**
 * Created by hz on 17-11-6.
 */

public class MemCache<K, V> implements IAppCache<V, K>, IDisakCache<K, V> {
    private LruCache<K, V> memCache = null;

    public MemCache() {
        memCache = new LruCache<K, V>((int)(Runtime.getRuntime().maxMemory() / 1024 / 8));
    }

    @Override
    public V getData(K p1) {
        return memCache.get(p1);
    }

    @Override
    public void setData(K k, V v) {
        memCache.put(k, v);
    }
}

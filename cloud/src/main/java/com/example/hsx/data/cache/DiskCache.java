package com.example.hsx.data.cache;

import android.content.Context;

import com.han.utils.HanLog;

import java.io.File;

/**
 * Created by hz on 17-11-6.
 */

public class DiskCache<K, V> implements IAppCache<V, K>, IDisakCache<K, V> {
    private File cacheDir = null;
    private DiskLruCache diskCache = null;

    public DiskCache(Context c) {
        cacheDir = getCacheDir(c);
        HanLog.write("OWNCLOUD", " Cache dir:" + cacheDir.getPath());

        try {
            diskCache = DiskLruCache.open(cacheDir, 1299, 1024, 1024 * 1024 * 10);
        } catch (Exception e){
            HanLog.write("OWNCLOUD", "DiskCache init excp:" + e.toString());
        }
    }

    @Override
    public V getData(K p1) {
        return null;
    }

    @Override
    public void setData(K k, V v) {
//        diskCache.get(k);
    }

    private File getCacheDir(Context c) {
        if (c == null)
            return null;

        //create cache dir on internal storage
        String path = c.getCacheDir().getPath();

        return new File(path + "/thumbnails");
    }
}

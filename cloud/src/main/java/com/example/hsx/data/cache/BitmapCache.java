package com.example.hsx.data.cache;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by hz on 17-11-6.
 */

public class BitmapCache {
    private MemCache<String, Bitmap> memCache = null;
    private DiskCache diskCache = null;
    private Context mCtx = null;

    public BitmapCache (Context c) {
        memCache = new MemCache<String, Bitmap>();
        diskCache = new DiskCache(c);
    }

    public Bitmap get(String k) {
        Bitmap ret = null;

        do {
            ret = memCache.getData(k);
            if (ret != null)
                break;

            ret = diskCache.getData(k);
            if (ret != null) {
                memCache.setData(k, ret);
            }

            break;
        } while(false);

        return ret;
    };

    public void set(String s, Bitmap b) {
        memCache.setData(s, b);
        diskCache.setData(s, b);
    }
}

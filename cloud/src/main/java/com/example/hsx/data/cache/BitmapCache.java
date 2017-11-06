package com.example.hsx.data.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.han.utils.HanLog;

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
                HanLog.write("OWNCLOUD", " Get disk data k:" + k + " v: bitmap is true.");
            } else {
                HanLog.write("OWNCLOUD", " Get disk data k:" + k + " v: bitmap is false.");
            }

            break;
        } while(false);

        return ret;
    };

    public void set(String s, Bitmap b) {

        memCache.setData(s, b);
        diskCache.setData(s, b);
        HanLog.write("OWNCLOUD", " Set disk data k:" + s);
    }
}

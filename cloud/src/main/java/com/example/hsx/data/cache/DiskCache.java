package com.example.hsx.data.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import com.han.utils.HanLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;

/**
 * Created by hz on 17-11-6.
 */

public class DiskCache implements IAppCache<Bitmap, String>, IDisakCache<String, Bitmap> {
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
    public Bitmap getData(String p1) {
        DiskLruCache.Snapshot sn = null;
        InputStream ios = null;
        Bitmap bmp = null;

        try {
            sn = diskCache.get((String) p1);
            ios = new BufferedInputStream(sn.getInputStream(0), 4096);
            bmp = BitmapFactory.decodeStream(ios);
        } catch (Exception e){}
        finally {
            try {
                ios.close();
                sn.close();
            } catch(Exception e){}
        }

        return bmp;
    }

    @Override
    public void setData(String k, Bitmap v) {
        DiskLruCache.Snapshot sn = null;
        DiskLruCache.Editor etor = null;
        OutputStream ots = null;

        try {
            etor = diskCache.edit(k);
            ots = new BufferedOutputStream(etor.newOutputStream(0), 4096);
            ((Bitmap)v).compress(Bitmap.CompressFormat.JPEG, 70, ots);

        } catch (Exception e){}
        finally {
            try {
                ots.close();
                diskCache.flush();
                etor.commit();
            }catch (Exception e){}
        }
    }

    private File getCacheDir(Context c) {
        if (c == null)
            return null;

        //create cache dir on internal storage
        String path = c.getCacheDir().getPath();
        File f = new File(path + "/thumbnails");

        String [] fStr = f.list();
        for (String n : fStr)
            HanLog.write("OWNCLOUD", " name:" + n);

        return f;
    }
}

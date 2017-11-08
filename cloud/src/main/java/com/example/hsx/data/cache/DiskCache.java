package com.example.hsx.data.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.han.utils.HanLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;

/**
 * Created by hz on 17-11-6.
 */

public class DiskCache implements IAppCache<Bitmap, String>, IDisakCache<String, Bitmap> {
    private File cacheDir = null;
    private DiskLruCache diskCache = null;
//    private DiskLruImageCache diskCachea = null;
    private static Context mCtx = null;

    public DiskCache(Context c) {
        mCtx = c;
        cacheDir = getCacheDir(c);
        HanLog.write("OWNCLOUD", " Cache dir:" + cacheDir.getPath());

        try {
            diskCache = DiskLruCache.open(cacheDir, 1, 1, 1024 * 1024 * 10);
//            diskCachea = new DiskLruImageCache(mCtx, "thumbnibls", 1024 * 1024, Bitmap.CompressFormat.JPEG, 70);
        } catch (Exception e){
            HanLog.write("OWNCLOUD", "DiskCache init excp:" + e.toString());
        }
    }

    @Override
    public Bitmap getData(String p1) {
        DiskLruCache.Snapshot sn = null;
        InputStream ios = null;
        Bitmap bmp = null;
        BufferedInputStream bis = null;

        try {
            sn = diskCache.get(p1);
            if (sn == null)
                return null;

            ios = sn.getInputStream(0);
            bis = new BufferedInputStream(ios);
            bmp = BitmapFactory.decodeStream(bis);
        } catch (Exception e){}
        finally {
            try {
                if (sn != null)
                    sn.close();
            } catch(Exception e){}
        }

        return bmp;
    }

    @Override
    public void setData(String k, Bitmap v) {
        DiskLruCache.Editor etor = null;
        OutputStream ots = null;

//        diskCachea.put(k, v);
        try {
            etor = diskCache.edit(k);
            if (etor == null) {
                HanLog.write("OWNCLOUD", " setData editor is null");
                return;
            }

            ots = new BufferedOutputStream(etor.newOutputStream(0), 4096);
            v.compress(Bitmap.CompressFormat.JPEG, 70, ots);
            ots.flush();
        } catch (Exception e){
            HanLog.write("OWNCLOUD", " setData excp:" + e.toString());
        } finally {
            try {

              if (etor != null) {
                  etor.commit();
              }

              if (diskCache != null) {
                    diskCache.flush();
              }

            }catch (Exception e){
                HanLog.write("OWNCLOUD", " setData close excp:" + e.toString());
            }
        }
    }

    private File getCacheDir(Context c) {
        if (c == null)
            return null;

        //create cache dir on internal storage
        String path = c.getCacheDir().getPath();
        HanLog.write("OWNCLOUD", " cache dir:" + path);
        File f = new File(path + "/thumbnails");
   /*
        InputStream is = null;
        if (f==null || f.list() == null || f.list().length <= 0);
        else {
            File [] fStr = f.listFiles();
            for (File n : fStr) {
                try {
//                    is = new FileInputStream(n.getAbsolutePath());
                    HanLog.write("OWNCLOUD", " name:" + n.getName() + " size:" + n.length()*//**is.available()*//* + " B");
//                    is.close();
                } catch (Exception e){
                }
            }
        }*/

        return f;

//      return getExternalCacheDir(c);
    }

    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static class DiskLruImageCache {

        private DiskLruCache mDiskCache;
        private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
        private int mCompressQuality = 70;
        private static final int APP_VERSION = 1;
        private static final int VALUE_COUNT = 1;
        private static final String TAG = "DiskLruImageCache";

        public DiskLruImageCache(Context context, String uniqueName, int diskCacheSize,
                                 Bitmap.CompressFormat compressFormat, int quality ) {
            try {
                final File diskCacheDir = getDiskCacheDir(context, uniqueName );
                mDiskCache = DiskLruCache.open( diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize );
                mCompressFormat = compressFormat;
                mCompressQuality = quality;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean writeBitmapToFile( Bitmap bitmap, DiskLruCache.Editor editor )
                throws IOException, FileNotFoundException {
            OutputStream out = null;
            try {
                out = new BufferedOutputStream( editor.newOutputStream( 0 ), 4096);//Utils.IO_BUFFER_SIZE );
                return bitmap.compress( mCompressFormat, mCompressQuality, out );
            } finally {
                if ( out != null ) {
                    out.close();
                }
            }
        }

        private File getDiskCacheDir(Context context, String uniqueName) {

            // Check if media is mounted or storage is built-in, if so, try and use external cache dir
            // otherwise use internal cache dir
            final String cachePath =
                    Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                            true ?
                            mCtx.getExternalCacheDir().getPath() :
                            context.getCacheDir().getPath();

            return new File(cachePath + File.separator + uniqueName);
        }

        public void put( String key, Bitmap data ) {

            DiskLruCache.Editor editor = null;
            try {
                editor = mDiskCache.edit( key );
                if ( editor == null ) {
                    return;
                }

                if( writeBitmapToFile( data, editor ) ) {
                    mDiskCache.flush();
                    editor.commit();
                    HanLog.write("OWNCLOUD", "image put on disk cache " + key );
/*                    if ( BuildConfig.DEBUG ) {
                        Log.d( "cache_test_DISK_", "image put on disk cache " + key );
                    }*/
                } else {
                    editor.abort();
                    HanLog.write("OWNCLOUD", "ERROR on: image put on disk cache " + key );
/*                    if ( BuildConfig.DEBUG ) {
                        Log.d( "cache_test_DISK_", "ERROR on: image put on disk cache " + key );
                    }*/
                }
            } catch (IOException e) {
                HanLog.write("OWNCLOUD", "ERROR on: image put on disk cache " + key );
/*                if ( BuildConfig.DEBUG ) {
                    Log.d( "cache_test_DISK_", "ERROR on: image put on disk cache " + key );
                }*/
                try {
                    if ( editor != null ) {
                        editor.abort();
                    }
                } catch (IOException ignored) {
                }
            }

        }

        public Bitmap getBitmap( String key ) {

            Bitmap bitmap = null;
            DiskLruCache.Snapshot snapshot = null;
            try {

                snapshot = mDiskCache.get( key );
                if ( snapshot == null ) {
                    return null;
                }
                final InputStream in = snapshot.getInputStream( 0 );
                if ( in != null ) {
                    final BufferedInputStream buffIn =
                            new BufferedInputStream( in, 4096);//Utils.IO_BUFFER_SIZE );
                    bitmap = BitmapFactory.decodeStream( buffIn );
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                if ( snapshot != null ) {
                    snapshot.close();
                }
            }

            HanLog.write("OWNCLOUD", bitmap == null ? "" : "image read from disk " + key);
/*            if ( BuildConfig.DEBUG ) {
                Log.d( "cache_test_DISK_", bitmap == null ? "" : "image read from disk " + key);
            }*/

            return bitmap;

        }

        public boolean containsKey( String key ) {

            boolean contained = false;
            DiskLruCache.Snapshot snapshot = null;
            try {
                snapshot = mDiskCache.get( key );
                contained = snapshot != null;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if ( snapshot != null ) {
                    snapshot.close();
                }
            }

            return contained;

        }

        public void clearCache() {
            HanLog.write("OWNCLOUD", "disk cache CLEARED");
/*            if ( BuildConfig.DEBUG ) {
                Log.d( "cache_test_DISK_", "disk cache CLEARED");
            }*/
            try {
                mDiskCache.delete();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        public File getCacheFolder() {
            return mDiskCache.getDirectory();
        }

    }
}

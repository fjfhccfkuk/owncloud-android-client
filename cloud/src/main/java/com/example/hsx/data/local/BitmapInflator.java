package com.example.hsx.data.local;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.example.hsx.data.DataInflator;
import com.example.hsx.data.cache.MemCache;
import com.example.hsx.data.models.BitmapInfo;
import com.example.hsx.data.models.PrivImages;
import com.example.hsx.data.models.PrivMedia;
import com.han.utils.HanDelay;
import com.han.utils.HanLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by hsx on 17-9-26.
 */

public class BitmapInflator implements DataInflator.LocalPicInflactor<ImageView>, DataInflator.LocalPicLoader{
    private static BitmapInflator mInflator = new BitmapInflator();
    private List<PrivImages> mSrcList = null;
    private Map<Integer, ImageView> mViewMap = null;
    private Thread mTask = null;
    private int max_size = 0;
    private int sharinkResolution = 128; //320 pix
    private Map<Integer, Bitmap> mThumbnail = null;
    private MemCache<String, Bitmap> bmpMemcache = null;

    private BitmapInflator(){
        mViewMap = new TreeMap<Integer, ImageView>();
        mSrcList  = new ArrayList<>();
        mThumbnail = new HashMap<>();
        bmpMemcache = new MemCache<>();//new LruCache<>((int)(Runtime.getRuntime().maxMemory() / 1024 / 8));

        mTask = new Thread(new InflaterRunnable());
        mTask.start();
    }

    public static BitmapInflator getInstance() {
        return mInflator;
    }

    @Override
    public void setSize(int initSize) {
        if (max_size == initSize)
            return;

        max_size = initSize;
    }

    @Override
    public BitmapInfo getData(ImageView obj, int pos) {
        Bitmap bmp = null;
        String picName = "";

        do {
//            bmp = mThumbnail.get(pos);
            picName = mSrcList.get(pos).getName();
            bmp = bmpMemcache.getData(picName);
            if (bmp != null)
                break;
            synchronized (mTask) {
                mViewMap.put(pos, obj);
            }

            notifyTaskRun();
        } while(false);

        return new BitmapInfo(bmp);
    }

    @Override
    public int getDataSize() {
        return mSrcList.size();
    }

    @Override
    public void update(Cursor c) {

        mSrcList.clear();

        int rowSize = c.getCount();
        for (int i = 0; i < rowSize; i ++) {
            c.moveToNext();

            PrivImages img = new PrivImages();
            mSrcList.add(img);
            generagePrivPicture(c, img);
        }

        mSrcList.size();


//        HanLog.write("OWNCLOUD", " LocalPictureViewAdapter.update() picSize:" + picSize);
    }


    private void generagePrivPicture(Cursor c, PrivMedia media) {
        int index = 0;
        String absolutePath = null;
        String tmpStr = null;
        int tmpInt = 0;
        int dataSize = 0;

        //data absolute path
        absolutePath = c.getString(c.getColumnIndex("_data"));
        media.setPath(absolutePath);

        //data size
        tmpStr = c.getString(c.getColumnIndex("_size"));
        dataSize = Integer.parseInt(tmpStr);
        media.setSize(dataSize);

        //data name "_display_name"
        tmpStr = c.getString(c.getColumnIndex("_display_name"));
        media.setName(tmpStr);

        //data create time "date_added"
        tmpStr = c.getString(c.getColumnIndex("date_added"));
        media.setCreatedTimeStamp(Integer.parseInt(tmpStr));

        //data type "bucket_display_name"
        tmpStr = c.getString(c.getColumnIndex("bucket_display_name"));
        media.setBelongTo(tmpStr);

        //data width "width"
        tmpInt = c.getInt(c.getColumnIndex("width"));
        media.setWidth(tmpInt);

        //date height "height"
        tmpInt = c.getInt(c.getColumnIndex("height"));
        media.setHeight(tmpInt);
    }

    private String splitAddr(String s) {
        String str = s.substring(s.indexOf("View{") + "View{".length(), s.indexOf(" V"));
        return str;
    }

    AsyncTask task ;

    private void notifyTaskRun() {
        synchronized (mTask) {
            mTask.notify();
        }
    }

    private class InflaterRunnable implements Runnable {
        int size = 0;
        Iterator<ImageView> ite;
        PrivImages media;

        @Override
        public void run() {
            int counter = 0;

            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    ImageView iv = (ImageView) msg.obj;
                    Bitmap bitmap = (Bitmap) iv.getTag();
                    iv.setImageBitmap(bitmap);
                }
            };

            while(true) {
                try {
                    synchronized (mTask) {
                        HanLog.write("OWNCLOUD", "[bitmap inflator] wait");
                        mTask.wait();
                    }

                    if (HanDelay.delay_ms(1000)) {
                        HanLog.write("OWNCLOUD", "[bitmap inflator] for circle break");
                        continue;
                    }

                    counter = 0;

                    Iterator ite = mViewMap.entrySet().iterator();
                    ImageView iv = null;
                    int pos = 0;
               //     for (Map.Entry<Integer, ImageView> entry : mViewMap.entrySet()) {
                    while(ite.hasNext()) {

                        synchronized (mTask) {
                            HanLog.write("OWNCLOUD", " counter:" + counter + " max_size:" + max_size);
                            if (counter++ > max_size) {
                                break;
                            }

                            Map.Entry entry = (Map.Entry) ite.next();

                            pos = (int) entry.getKey();
                            iv = (ImageView) entry.getValue();
                        }

                        Bitmap bitmap = getThumbnail(pos);

                        iv.setTag(bitmap);

                        ImageViewAddBitmap(handler, iv);

                        if (HanDelay.delay_ms(10)) {
                            HanLog.write("OWNCLOUD", "[bitmap inflator] for circle break");
                            break;
                        }
                    }

                    mViewMap.clear();
                } catch (Exception e){
                    HanLog.write("OWNCLOUD", "[bitmap inflator] excp:" + e.toString());
                }
            }
        }

        private void ImageViewAddBitmap(Handler handler, ImageView iv) {
            try {
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = iv;
                handler.sendMessage(msg);
//                                img.setImageBitmap(bitmap);
            } catch (Exception e){
                HanLog.write("OWNCLOUD", "[bitmap inflator] excp:" + e.toString());
            }
        }

        private Bitmap getThumbnail(int pos) {

            PrivImages media = mSrcList.get(pos);
            if (media == null)
                throw new RuntimeException("src list size:" + mSrcList.size() + " req pos:" + pos + " 不应为空");

            Bitmap bitmap = null;

            do {
//                bitmap = mThumbnail.get(pos);
                bitmap = bmpMemcache.getData(media.getName());
                if (bitmap != null)
                    break;

                size = Math.max(media.getWidth(), media.getHeight());
                if (size > sharinkResolution) {
                    bitmap = thumbnail(media.getPath(), media.getWidth(), media.getHeight());
                    bmpMemcache.setData(media.getName(), bitmap);
//                    mThumbnail.put(pos, bitmap);
                } else {
                    bitmap = BitmapFactory.decodeFile(media.getPath());
                }

            } while (false);

            return bitmap;
        }

        private Bitmap thumbnail(String path, int w, int h) {
            Bitmap b = null;

            int bW = w;
            int bH = h;
            int simpleSize = 1;

            while(bW > sharinkResolution || bH >sharinkResolution) {
                bW /= 2;
                bH /= 2;

                simpleSize *= 2;
            }

            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inSampleSize = simpleSize;
            b = BitmapFactory.decodeFile(path, ops);

            return b;
        }
    }
}

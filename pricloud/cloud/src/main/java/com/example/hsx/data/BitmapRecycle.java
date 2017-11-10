package com.example.hsx.data;

import android.graphics.Bitmap;

import com.han.utils.HanDelay;
import com.han.utils.HanLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hsx on 17-9-25.
 */

public class BitmapRecycle implements IRecycle<Bitmap> {
    private static List<Bitmap> mGarbigeList = null;
    private static BitmapRecycle mObj = new BitmapRecycle();
    private Thread mThread = null;

    private BitmapRecycle(){
        mGarbigeList = new ArrayList<Bitmap>();
        mThread = new Thread(new RecycleRunnable());
        mThread.start();
    }

    public static BitmapRecycle getInstance() {
        return mObj;
    }

    @Override
    public void add(Bitmap o) {
        mGarbigeList.add(o);
    }

    private class RecycleRunnable implements Runnable {
        Iterator<Bitmap> ite = null;

        @Override
        public void run() {
            while(true) {
                if (mGarbigeList.size() <= 0) {
                    HanDelay.delay_s(1);
                    continue;
                }

                ite = mGarbigeList.iterator();
                doRecycle(ite);
            }
        }

        private void doRecycle(Iterator<Bitmap> ite) {
            if (ite == null)
                return;

            try {
                while (ite.hasNext()) {
                    Bitmap b = ite.next();
                    if (!b.isRecycled()) {
                        b.recycle();
                        ite.remove();
                    }
                }
            } catch (Exception e){
                HanLog.write(" BitmapRecycle.doRecycle() excp:" + e.toString());
            } finally {
                System.gc();
            }
        }
    }
}

package com.example.hsx.ui.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.busap.utils.BusDelay;
import com.busap.utils.BusLog;
import com.example.hsx.myapplication.R;

/**
 * Created by hsx on 17-8-27.
 */

public class TestActivity extends BaseActivity {

    private Thread th = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurfaceView sv = new SurfaceView(this);

        BusLog.write("OWNCLOUD", " TestActivity onCreate");
        ImageView iv = new ImageView(this);
        Bitmap bmp = BitmapFactory.decodeFile("//sdcard/moon.png");
//        iv.setImageBitmap(bmp);

//        setContentView(v);
        addContentView(sv, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

//        Drawable d = BitmapDrawable.createFromPath("//sdcard/moon.png");
        th = new Thread(new DrawRunnable(sv, bmp));
    }


    private class DrawRunnable implements Runnable {

        private SurfaceView sv = null;
        private Bitmap draw = null;
        public DrawRunnable(SurfaceView v, Bitmap d) {
            this.sv = v;
            this.draw = d;
        }

        @Override
        public void run() {

            BusDelay.delay_s(10);

            Canvas c = this.sv.getHolder().lockCanvas();

            c.drawColor(Color.BLUE);
            c.drawBitmap(this.draw, 0, 0, new Paint());
            this.sv.getHolder().unlockCanvasAndPost(c);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        th.start();
    }
}

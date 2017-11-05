package com.example.hsx.data.models;

import android.graphics.Bitmap;

/**
 * Created by hz on 17-11-1.
 */

public class BitmapInfo implements IBmpOperation{
    private Bitmap mBmp = null;

    public BitmapInfo (Bitmap bmp) {
        this.mBmp = bmp;
    }

    public Bitmap getBmp() {
        return mBmp;
    }

}

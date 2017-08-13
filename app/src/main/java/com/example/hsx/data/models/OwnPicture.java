/**
 * Created by hsx on 17-8-6.
 */
package com.example.hsx.data.models;

import android.graphics.Bitmap;

public class OwnPicture {

    /**
     * picture name
     */
    private String picName;

    private Bitmap mBitmap;

    /**
     * set picName
     * @param s pic name
     */
    public void setName(String s) {
        this.picName = s;
    }

    /**
     * get picName
     * @return picName
     */
    public String getName() {
        return this.picName;
    }

    public void setBitmap(Bitmap b) {
        this.mBitmap = b;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }
}

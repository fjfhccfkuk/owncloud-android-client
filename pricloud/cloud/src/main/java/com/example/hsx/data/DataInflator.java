package com.example.hsx.data;

import android.database.Cursor;
import android.graphics.Bitmap;

import com.example.hsx.data.models.BitmapInfo;

/**
 * Created by hsx on 17-9-26.
 */

public interface DataInflator<A> {
    //


    interface LocalPicLoader {
        //data source invoke this method
        void update(Cursor c);
    }

    interface LocalPicInflactor<A> {
        //set data windows size/count
        void setSize(int initSize);

        //data user invoke this method for specify position
        BitmapInfo getData(A obj, int pos);

        //data user invoke this method to get data count
        int getDataSize();
    }
}

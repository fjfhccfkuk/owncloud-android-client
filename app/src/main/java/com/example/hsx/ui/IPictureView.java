package com.example.hsx.ui;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by hsx on 17-8-7.
 */

public class IPictureView {

    public interface Presenter {
        void getPhotoName(ImageView a, int index);
    }

    public interface ViewPic {
        void showText(Bitmap s);
        void resetText();
    }
}

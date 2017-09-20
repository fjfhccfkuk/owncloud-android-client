package com.example.hsx.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.hsx.data.DataRepository;
import com.example.hsx.data.DataSource;
import com.example.hsx.data.models.PrivMedia;
import com.example.hsx.ui.IPictureView;

/**
 * Created by hsx on 17-8-7.
 */

public class OwnPicturePresenter implements IPictureView.Presenter{

    private IPictureView.ViewPic mTxt = null;
    private DataRepository mData = null;

    public OwnPicturePresenter(IPictureView.ViewPic txt, DataRepository data) {
        this.mTxt = txt;
        this.mData = data;
    }

    @Override
    public void getPhotoName(ImageView b, int index) {

        if (mData != null) {
            mData.getPhoto(index, new PhotoDone<ImageView>(b){
                @Override
                void onPhotoSucc(ImageView a, Bitmap b) {
                    a.setImageBitmap(b);
                }

                @Override
                void onPhotoErr(ImageView a) {
                }
            });
        }
    }

    private static abstract class PhotoDone<T> implements DataSource.PhotoCallback {
        private T obj = null;

        public PhotoDone(T a) {
            this.obj = a;
        }

        @Override
        public void onSuccess(PrivMedia p) {
     //       onPhotoSucc(obj, p.getBitmap());
        }

        @Override
        public void onError(String s) {
            onPhotoErr(obj);
        }

        abstract void onPhotoSucc(T a, Bitmap b);
        abstract void onPhotoErr(T a);
    }
}

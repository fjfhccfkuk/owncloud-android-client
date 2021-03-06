package com.example.hsx.data.remote;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.hsx.data.DataSource;
import com.example.hsx.data.models.PrivMedia;

/**
 * Created by hsx on 17-8-7.
 */

public class SSHDataSource extends DataSource {

//    private String mRemoteUrl = "http://www.fjfhccfkuk.vip:8020/dev/bg/";
    private String mRemoteUrl = "http://img.ivsky.com/img/tupian/pre/201706/28/sanjiaomei-";

    private static SSHDataSource mSource = null;
    private static INetwork mNet = null;
    private PhotoCallback mCb = null;

    private SSHDataSource(INetwork net){
        this.mNet  = net;
    }

    public synchronized static DataSource getInstance(INetwork net) {
        if (mSource == null)
            mSource = new SSHDataSource(net);

        return mSource;
    }

    @Override
    public void getPhoto(int index, PhotoCallback c) {
        //do request photos
        boolean reqSucess = false;

        this.mCb = c;
        String url = mRemoteUrl + ((index>9)?"0":"00") + index + ".jpg";

        Log.i("TEST", " remote date source.getPhoto() url:" + url);
        mNet.request(url, new DataResponse(c){
            @Override
            void onResponse(PhotoCallback b, Bitmap s) {
/*                PrivMedia op = new PrivMedia();
                op.setBitmap(s);
                b.onSuccess(op);*/
            }

            @Override
            void onError(PhotoCallback b, String err) {
                b.onError(err);
            }
        });
    }

    private static abstract class DataResponse implements INetwork.NetworkResponse {
        private PhotoCallback mBack = null;

        public DataResponse (PhotoCallback b) {
            this.mBack = b;
        }

        @Override
        public void onSuccess(Bitmap s) {
            onResponse(this.mBack, s);
        }

        @Override
        public void onFailure(String s) {
            onError(this.mBack, s);
        }

        abstract void onResponse(PhotoCallback b, Bitmap s);
        abstract void onError (PhotoCallback b, String err);
    }
}

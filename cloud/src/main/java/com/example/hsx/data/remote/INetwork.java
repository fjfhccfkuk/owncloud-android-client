package com.example.hsx.data.remote;

import android.graphics.Bitmap;

/**
 * Created by hsx on 17-8-7.
 */

public interface INetwork {
    void request(String url, NetworkResponse cb);

    interface NetworkResponse {
        void onSuccess(Bitmap s);
        void onFailure(String s);
    }
}

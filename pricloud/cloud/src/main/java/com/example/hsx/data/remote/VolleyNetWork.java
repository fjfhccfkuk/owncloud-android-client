package com.example.hsx.data.remote;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import static com.android.volley.toolbox.Volley.newRequestQueue;

/**
 * Created by hsx on 17-8-7.
 */

public class VolleyNetWork implements INetwork {
    private static VolleyNetWork mNetwork = null;
    private RequestQueue mNetworkReqQueue = null;
    private NetworkResponse mPhotoCb = null;

    private VolleyNetWork(Context c){
        if (mNetworkReqQueue == null)
            mNetworkReqQueue = newRequestQueue(c);//new RequestQueue(null, null);
    }

    public synchronized static INetwork getInstance(Context c) {
        if (mNetwork == null)
            mNetwork = new VolleyNetWork(c);

        return mNetwork;
    }

    @Override
    public void request(String url, NetworkResponse cb) {
        Log.i("TEST", " volley.request");
        this.mPhotoCb = cb;

        if (mNetworkReqQueue == null) {
            Log.i("TEST", " volley.RequestQueue is null");
            mPhotoCb.onFailure(" volley.requestQueue is null");
            return;
        }

        Log.i("TEST", " volley ImageRequest ...");
//        StringRequest strReq = new StringRequest(Request.Method.GET, url, new RequestSuccess(mPhotoCb), new RequestError(mPhotoCb));
        ImageRequest imgReq = new ImageRequest(url, new RequestSuccess(mPhotoCb), 0, 0, Bitmap.Config.RGB_565, new RequestError(mPhotoCb));
//        strReq.setRetryPolicy(new DefaultRetryPolicy(1000, 0, 0));

//        mNetworkReqQueue.add(strReq);
        mNetworkReqQueue.add(imgReq);
    }

    private class RequestSuccess implements Response.Listener<Bitmap> {
        NetworkResponse mCb = null;

        public RequestSuccess(NetworkResponse cb) {
            mCb = cb;
        }

        @Override
        public void onResponse(Bitmap s) {
            Log.i("TEST", " volley response Success :" + s);
//            mCb.onSuccess(s);
            mCb.onSuccess(s);
        }
    }

    private class RequestError implements Response.ErrorListener {
        NetworkResponse mCb = null;

        public RequestError(NetworkResponse cb) {
            mCb = cb;
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.i("TEST", " volley response Error :" + volleyError.toString());
            mCb.onFailure(volleyError.toString());
        }
    }
}

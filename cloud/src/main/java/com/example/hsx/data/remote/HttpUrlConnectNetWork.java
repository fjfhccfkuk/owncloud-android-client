package com.example.hsx.data.remote;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by hsx on 17-8-21.
 */

public class HttpUrlConnectNetWork implements INetwork{
    private static WeakReference<Context> mCtx = null;


    @Override
    public void request(String url, NetworkResponse cb) {

    }

    private HttpUrlConnectNetWork(Context c) {
        try {
            URL url = new URL("https://www.demo.owncloud.org");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
        } catch (Exception e){}
    }
}

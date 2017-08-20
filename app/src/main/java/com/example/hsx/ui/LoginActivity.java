package com.example.hsx.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.busap.utils.BusLog;
import com.example.hsx.myapplication.R;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hsx on 17-8-14.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.login_layout);

        new Thread(new sslRunnable()).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //test
    private class sslRunnable implements  Runnable {
        @Override
        public void run() {
            try {

                URL url = new URL("https://demo.owncloud.org");
                
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                int resCode = con.getResponseCode();

            //    switch (resCode)
                {
                    BusLog.write("OWNCLOUD", " response Code:" + resCode);
                }

            } catch (Exception e){
                BusLog.write("OWNCLOUD", " response Excp:" + e.toString());
            }
        }
    }
}

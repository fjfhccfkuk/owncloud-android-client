package com.example.hsx.ui.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.han.utils.HanLog;
import com.example.hsx.myapplication.R;
import com.example.hsx.presenter.LoginPresenter;
import com.example.hsx.presenter.Presenter;
import com.example.hsx.ui.Dialog.LoginProcessDialog;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 * Created by hsx on 17-8-14.
 */

public class LoginActivity extends BaseActivity {

    private Presenter.Login loginPresenter = null;
    private LoginProcessDialog loginDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        loginDialog = LoginProcessDialog.getInstance(this);
        loginPresenter = LoginPresenter.getInstance(this, loginDialog);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loginPresenter.login();
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

                SSLContext sslCtx = SSLContext.getInstance("TLS");

                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setConnectTimeout(3000);
                con.setRequestMethod("GET");
                con.setSSLSocketFactory(null);
                con.setSSLSocketFactory(sslCtx.getSocketFactory());

                int resCode = con.getResponseCode();

            //    switch (resCode)
                {
                    HanLog.write("OWNCLOUD", " response Code:" + resCode);
                }

            } catch (Exception e){
                HanLog.write("OWNCLOUD", " response Excp:" + e.toString());
            }
        }
    }
}

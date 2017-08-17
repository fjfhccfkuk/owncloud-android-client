package com.example.hsx.ui;

import android.accounts.Account;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.hsx.myapplication.R;
import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientFactory;
import com.owncloud.android.lib.common.OwnCloudCredentials;
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory;
import com.owncloud.android.lib.common.operations.OnRemoteOperationListener;
import com.owncloud.android.lib.common.operations.RemoteOperation;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.files.FileUtils;
import com.owncloud.android.lib.resources.files.ReadRemoteFileOperation;
import com.owncloud.android.lib.resources.files.ReadRemoteFolderOperation;
import com.owncloud.android.lib.resources.files.RemoteFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by hsx on 17-8-14.
 */

public class Splash extends BaseActivity implements Jump{

    private Handler ownHandler = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);

        Uri ser = Uri.parse("https://demo.owncloud.org");
        OwnCloudClient client = OwnCloudClientFactory.createOwnCloudClient(ser, this, true);

        client.setCredentials(OwnCloudCredentialsFactory.newBasicCredentials("admin", "admin"));
     //   client.setCredentials(OwnCloudCredentialsFactory.newBearerCredentials("admin"));

        ownHandler = new Handler();

        ReadRemoteFolderOperation remoteFils = new ReadRemoteFolderOperation(FileUtils.PATH_SEPARATOR);
        remoteFils.execute(client, new OnRemoteOperationListener() {
            @Override
            public void onRemoteOperationFinish(RemoteOperation caller, RemoteOperationResult result) {
                if (result.isSuccess()) {
                    Log.i("OWNCLOUD", " Success to get remote files");
          //          List<RemoteFile> files = result.getData();
                    List< Object > objs = (ArrayList<Object>) result.getData();

                    int len = objs.size();

                    for (int i = 0; i < len; i ++) {
                        RemoteFile rf = (RemoteFile) objs.get(i);
                        Log.i("OWNCLOUD", " file [" + i + "] : is " + rf.getMimeType() + " path:" + rf.getRemotePath());
                    }

                } else {
                    Log.i("OWNCLOUD", " Failed to get remote files err:" + result.getException() + " " + result.getAuthenticateHeader() + " " + result.getHttpCode());
                }
            }
        }, ownHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent i = new Intent();
        i.setClass(this, LoginActivity.class);

        PendingIntent pi = PendingIntent.getActivities(this, 990, new Intent[]{i}, PendingIntent.FLAG_CANCEL_CURRENT);

        new Thread(new JumpRunnable(pi)).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void Jump2NextActivity() {
        Intent i = new Intent();
        i.setClass(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private class JumpRunnable implements Runnable {
        private Jump mJ = null;
        private PendingIntent mPi = null;

        public JumpRunnable(PendingIntent pi) {
            this.mJ = null;
            this.mPi = pi;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
//                this.mJ.Jump2NextActivity();

                try {
                    this.mPi.send();
                } catch (Exception e){}
            } catch (Exception e){}
        }
    }
}

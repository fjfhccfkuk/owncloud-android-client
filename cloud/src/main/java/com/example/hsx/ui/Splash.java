package com.example.hsx.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.busap.utils.BusLog;
import com.example.hsx.data.models.PrivCloudAccount;
import com.example.hsx.myapplication.R;
import com.example.hsx.ui.Activities.BaseActivity;
import com.example.hsx.ui.Activities.LoginActivity;
import com.example.hsx.ui.Activities.MainActivity;
import com.example.hsx.ui.Activities.TestActivity;

import java.util.List;

/**
 * Created by hsx on 17-8-14.
 */

public class Splash extends BaseActivity {

    private Handler ownHandler = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pricloud_layout);

        List<PrivCloudAccount> accounts = getAccounts(this);
        if (accounts == null || accounts.size() <= 0) {
            BusLog.write("OWNCLOUD", " accounts:" + accounts.toString());
            //跳转登录界面

            Intent i = new Intent();
//            i.setClass(this, LoginActivity.class);
            i.setClass(this, MainActivity.class);
//            i.setClass(this, TestActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
            Jump2NextActivity(pi, 3);
            return;
        }

        //jump to file display activity
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        Jump2NextActivity(pi, 3);

        /**
         * 自动登录到服务器,跳转到展示页面
         */
/*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

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
        }, ownHandler);*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent i = new Intent();
        i.setClass(this, LoginActivity.class);

        Log.i("OWNCLOUD", "onResume");
//        PendingIntent pi = PendingIntent.getActivities(this, 990, new Intent[]{i}, PendingIntent.FLAG_CANCEL_CURRENT);

//        new Thread(new JumpRunnable(pi)).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("OWNCLOUD", "onPause");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("OWNCLOUD", "onDestroy");
    }
}

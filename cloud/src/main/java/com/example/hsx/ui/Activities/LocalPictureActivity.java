package com.example.hsx.ui.Activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hsx.adapter.LocalPictureListViewAdapter;
import com.example.hsx.presenter.Presenter;
import com.example.hsx.ui.Fragments.local.PictureListView;
import com.example.hsx.ui.Fragments.local.PictureLoaderCallback;
import com.han.utils.HanDelay;
import com.han.utils.HanLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsx on 17-8-27.
 */

public class LocalPictureActivity extends BaseActivity {

    private Thread th = null;
    private LocalPictureListViewAdapter mAdapter = null;
    private ListView mListView = null;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        addContentView(btn, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        String [] perms = new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        mAdapter = new LocalPictureListViewAdapter(this);
        mListView = new PictureListView(this);
//        mListView.setBackgroundColor(Color.BLUE);
        addContentView(mListView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mListView.setAdapter(mAdapter);

        permissionReq(perms);
    }

    private void permissionReq (String [] r) {
        if (Build.VERSION.SDK_INT < 23)
            return;

        //collect which permission should be request.
        List<String> shouldReqList = new ArrayList<String>();

        for (String per : r) {
            if (checkSelfPermission(per) != PackageManager.PERMISSION_GRANTED) {
                HanLog.write("OWNCLOUD", " fun permissionReq() check perms :" + per + " doesn't granted");
                shouldReqList.add(per);
            } else {
                HanLog.write("OWNCLOUD", " fun permissionReq() check perms :" + per + " has been granted");
            }
        }

        //do request permission
        if(shouldReqList.size() > 0) {
            int index = 0;
            String shouldRequestPems[] = new String[shouldReqList.size()];

            for (int i = 0; i < shouldReqList.size(); i ++) {
                shouldRequestPems[index ++] = shouldReqList.get(i);
            }

            HanLog.write("OWNCLOUD", " fun permissionReq() going to request permission:" + shouldRequestPems.toString());
            requestPermissions(shouldRequestPems, 100);
        } else {
            initLoaderManager();
            HanLog.write("OWNCLOUD", " fun permissionReq() no permission will be request.");
        }
    }

    private void initLoaderManager() {
//        getSupportLoaderManager();
        getLoaderManager().initLoader(2, null, new PictureLoaderCallback(this, this.mAdapter));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class GatherPhotosRunnable implements Runnable {
        private PendingIntent mPi = null;

        GatherPhotosRunnable (PendingIntent pi) {
            this.mPi = pi;
        }

        @Override
        public void run() {
            HanDelay.delay_s(10);

            try {
                HanLog.write("OWNCLOUD", " GatherPhotosRunnable. read to send intent");
                this.mPi.send();
            } catch(Exception e) {}
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean runLoader = true;
        int pems_size = permissions.length;

        for (int i = 0; i < pems_size; i ++) {
            switch (permissions[0]) {
                case Manifest.permission.READ_CONTACTS:
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        runLoader = false;
                    }
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        runLoader = false;
                    }
                    break;
                case Manifest.permission.CAMERA:
                    break;
                default:
                    break;
            }
        }

        if (runLoader) {
            //do get contacts fun
            initLoaderManager();
        }
    }
}

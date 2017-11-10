package com.example.hsx.ui.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by hsx on 17-8-21.
 */

public abstract class ProcessDialog {
    private WeakReference<Context> mCtx = null;

    private Handler mHandler = null;

    public ProcessDialog() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public interface view {
        void showProgressPercent(int p);
        void showProgressError();
        void onLoginOn();
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}

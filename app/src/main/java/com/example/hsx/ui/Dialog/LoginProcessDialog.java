package com.example.hsx.ui.Dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by hsx on 17-8-21.
 */

public class LoginProcessDialog extends ProcessDialog implements ProcessDialog.view {
    private WeakReference<Context> mCtx = null;
    private static LoginProcessDialog mDialog = null;
    private AlertDialog dialog = null;

    private LoginProcessDialog(Context c){
        mCtx = new WeakReference<Context>(c);
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx.get());
        builder.setTitle("正在登录...");
        builder.setMessage("This is a test msg.");
        dialog = builder.create();
    }

    public static synchronized LoginProcessDialog getInstance(Context c) {
        if (mDialog == null) {
            mDialog = new LoginProcessDialog(c);
        }

        return mDialog;
    }

    @Override
    public void showProgressPercent(int p) {
 //       dialog.show();
    }

    @Override
    public void showProgressError() {

    }

    @Override
    public void onLoginOn() {

    }
}

package com.example.hsx.presenter;

import android.content.Context;

import com.example.hsx.ui.Dialog.ProcessDialog;

import java.lang.ref.WeakReference;

/**
 * Created by hsx on 17-8-21.
 */

public class LoginPresenter implements Presenter.Login{
    private static LoginPresenter mPresenter = null;
    private ProcessDialog.view mView = null;
    private WeakReference<Context> mCtx = null;

    private LoginPresenter(Context c, ProcessDialog.view v) {
        this.mCtx = new WeakReference<Context>(c);
        this.mView = v;
    }

    public static synchronized Presenter.Login getInstance(Context c, ProcessDialog.view v) {
        if (mPresenter == null)
            mPresenter = new LoginPresenter(c, v);

        return mPresenter;
    }

    @Override
    public void login() {
        new Thread(new loginRunnable(this.mView)).start();
    }

    @Override
    public void interrupt() {
    }

    private class loginRunnable implements Runnable {
        private ProcessDialog.view v = null;
        public loginRunnable(ProcessDialog.view v) {
            this.v = v;
        }

        @Override
        public void run() {
            v.showProgressPercent(0);
        }
    }
}

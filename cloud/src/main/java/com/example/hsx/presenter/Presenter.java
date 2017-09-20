package com.example.hsx.presenter;

import android.database.Cursor;

import com.example.hsx.ui.Dialog.LoginProcessDialog;

/**
 * Created by hsx on 17-8-21.
 */

public abstract class Presenter {

    public interface Login {
        void login();
        void interrupt();
    }

    public interface PictureListView {
        void update(Cursor c);
    }
}

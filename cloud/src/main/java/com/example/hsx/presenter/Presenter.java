package com.example.hsx.presenter;

import com.example.hsx.ui.Dialog.LoginProcessDialog;

/**
 * Created by hsx on 17-8-21.
 */

public abstract class Presenter {

    public interface Login {
        void login();
        void interrupt();
    }
}

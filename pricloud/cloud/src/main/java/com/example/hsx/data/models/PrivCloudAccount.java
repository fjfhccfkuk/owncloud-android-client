package com.example.hsx.data.models;

import android.accounts.Account;
import android.app.Activity;

/**
 * Created by hsx on 17-8-18.
 */

public class PrivCloudAccount implements Cloneable{
    private String user = null;
    private String passwd = null;

    public PrivCloudAccount(String name, String passwd) {
        this.user = name;
        this.passwd = passwd;
    }

    public String getUserName() {
        return this.user;
    }

    public String getPasswd() {
        return this.passwd;
    }

}

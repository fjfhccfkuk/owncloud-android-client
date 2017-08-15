package com.example.hsx.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by hsx on 17-8-14.
 */

public class BaseActivity extends Activity {
    private static final String PRIV_DATA = "owncloud";
    private static SharedPreferences ownPreference = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}

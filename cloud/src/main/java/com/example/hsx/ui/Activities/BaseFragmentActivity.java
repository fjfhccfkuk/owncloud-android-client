package com.example.hsx.ui.Activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.busap.utils.BusDelay;
import com.example.hsx.data.models.PrivCloudAccount;
import com.example.hsx.ui.Jump;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hsx on 17-8-14.
 */

public class BaseFragmentActivity extends FragmentActivity {
    private static final String PRIV_DATA = "owncloud";
    private static SharedPreferences ownPreference = null;
    private static List<PrivCloudAccount> listAccount = new ArrayList<PrivCloudAccount>();
    private static ExecutorService mEs = Executors.newFixedThreadPool(1);

    public static void Jump2NextActivity (PendingIntent pi, int delay) {
        mEs.execute(new JumpRunnable(pi, delay));
    }

    public static List<PrivCloudAccount> getAccounts(Context c) {
        PrivCloudAccount account = null;
        listAccount.clear();

        if (ownPreference == null) {
            ownPreference = c.getSharedPreferences("account", Context.MODE_PRIVATE);
        }

        String name = ownPreference.getString("name", "none");
        String passwd = ownPreference.getString("passwd", "none");

        if (!name.equals("none") && !passwd.equals("none")) {
            account = new PrivCloudAccount(name, passwd);
            listAccount.add(account);
        }

        return listAccount;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    private static class JumpRunnable implements Runnable {
        private Jump mJ = null;
        private PendingIntent mPi = null;
        private int delayTime = 0;

        public JumpRunnable(PendingIntent pi, int delay) {
            if (pi == null || delay < 0) {
                throw new RuntimeException(" JumpRunnable, 参数非法");
            }

            this.mJ = null;
            this.mPi = pi;
            this.delayTime = delay;
        }

        @Override
        public void run() {
            try {
                BusDelay.delay_s(this.delayTime);
                try {
                    this.mPi.send();
                } catch (Exception e){}
            } catch (Exception e){}
        }
    }
}

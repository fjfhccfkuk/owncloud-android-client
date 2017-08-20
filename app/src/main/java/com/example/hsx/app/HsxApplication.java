package com.example.hsx.app;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

import com.busap.utils.BusCrash.BusApplication;
import com.busap.utils.BusLog;

/**
 * Created by hsx on 17-8-14.
 */

public class HsxApplication extends BusApplication {
    @Override
    public String getLogTag() {
        return "Crash_PrivCloud";
    }

    @Override
    public void onExcp(String err) {
        BusLog.write("OWNCLOUD", "Excp:" + err);
        Toast t = Toast.makeText(this, "Excp: \n" + err, Toast.LENGTH_LONG);
        t.show();
    }
}

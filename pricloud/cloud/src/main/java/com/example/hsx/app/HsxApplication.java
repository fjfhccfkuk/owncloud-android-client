package com.example.hsx.app;

import android.widget.Toast;

import com.han.utils.HanCrash.HanApplication;
import com.han.utils.HanLog;

/**
 * Created by hsx on 17-8-14.
 */

public class HsxApplication extends HanApplication {
    @Override
    public String getLogTag() {
        return "Crash_PrivCloud";
    }

    @Override
    public void onExcp(String err) {
        HanLog.write("OWNCLOUD", "Excp:" + err);
        Toast t = Toast.makeText(this, "Excp: \n" + err, Toast.LENGTH_LONG);
        t.show();
    }
}

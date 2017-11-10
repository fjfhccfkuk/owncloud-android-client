package com.busap.utils.BusCrash;

import android.app.Application;

import com.busap.utils.BusLog;

/**
 * Created by hsx on 17-8-4.
 */

public abstract class BusApplication extends Application {
    String logTag = "app";

    @Override
    public void onCreate() {
        super.onCreate();

        String str = getLogTag();

        if (null != str) {
            logTag = str;
        }

        BusLog.writeDisk(logTag,  this.getPackageName() + " application startup ");
        BusExcpCatcher mCatcher = BusCatchCrashInfo.ConstructInstance(this);
        mCatcher.setGather(new ICreashInfo() {
            @Override
            public void gatherCrashInfo(String e) {
                BusLog.writeDisk(logTag, e);

                onExcp(e);
            }
        });
    }

    public abstract void onExcp(String err);
    public abstract String getLogTag();
}

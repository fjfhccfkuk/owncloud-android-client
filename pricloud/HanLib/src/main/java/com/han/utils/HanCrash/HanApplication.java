package com.han.utils.HanCrash;

import android.app.Application;

import com.han.utils.HanLog;

/**
 * Created by hsx on 17-8-4.
 */

public abstract class HanApplication extends Application {
    String logTag = "app";

    @Override
    public void onCreate() {
        super.onCreate();

        String str = getLogTag();

        if (null != str) {
            logTag = str;
        }

        HanLog.writeDisk(logTag,  this.getPackageName() + " application startup ");
        HanExcpCatcher mCatcher = HanCatchCrashInfo.ConstructInstance(this);
        mCatcher.setGather(new ICreashInfo() {
            @Override
            public void gatherCrashInfo(String e) {
                HanLog.writeDisk(logTag, e);

                onExcp(e);
            }
        });
    }

    public abstract void onExcp(String err);
    public abstract String getLogTag();
}

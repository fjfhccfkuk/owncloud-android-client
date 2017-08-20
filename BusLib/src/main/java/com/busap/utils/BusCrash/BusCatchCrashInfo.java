package com.busap.utils.BusCrash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by hsx on 17-8-3.
 */

public class BusCatchCrashInfo implements Thread.UncaughtExceptionHandler, BusExcpCatcher {
    private static BusCatchCrashInfo mCCI = null;
    private static ICreashInfo mGather = null;
    private static String verName = null;

    private static class CatchCreashInner {
        private static Context mCtx = null;
        private static BusCatchCrashInfo mCrashInfo = new BusCatchCrashInfo();
        private static Thread.UncaughtExceptionHandler mDefCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static BusExcpCatcher ConstructInstance(Context c) {
        if (null == mCCI) {
            mCCI = CatchCreashInner.mCrashInfo;
            CatchCreashInner.mCtx = c;
            Thread.setDefaultUncaughtExceptionHandler(mCCI);
        }

        return mCCI;
    }

    @Override
    public void setGather(ICreashInfo c) {
        mGather = c;

        //get package info
        {
            try {
                Context ctx = CatchCreashInner.mCtx;
                PackageManager pm = ctx.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);

                if (null != pi) {
                    verName = pi.versionName + "";
                }

            } catch (Exception e){
            }
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ExcpDataProcess(ex);
    }

    private void ExcpDataProcess(Throwable e) {
        String excpStr = null;

        if (null == e)
            return;

        Writer strWriter = null;
        PrintWriter strPritWriter = null;

        try {
            strWriter = new StringWriter();
            strWriter.append("\n");
            strPritWriter = new PrintWriter(strWriter);
            e.printStackTrace(strPritWriter);
            strWriter.append("\n");

            Throwable t = e.getCause();

            while (null != t) {
                t.printStackTrace(strPritWriter);
                strWriter.append("\n");
                t = t.getCause();
            }

            excpStr = strWriter.toString();
        } catch (Exception e1) {}
        finally {
            try {
                if (null != strPritWriter) strPritWriter.close();
                if (null != strWriter) strWriter.close();
            } catch (Exception e2){}
        }

        if (null == excpStr)
            return;

        mGather.gatherCrashInfo("[ verName:" + verName + "]" + excpStr);
    }
}

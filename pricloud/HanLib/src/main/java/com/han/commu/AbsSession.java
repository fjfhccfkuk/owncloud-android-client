/**
 * Created by hsx on 17-6-6.
 */

package com.han.commu;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsSession extends AbsSocket{

    //apk包名
    private Context mCtx = null;

    //上一次访问此session的escape time.
    private int mLastTime = 0;

    private static Map<String, ROLE> mPackList = new HashMap<String, ROLE>(){};
    static {
        mPackList.put("com.busap.remctrlser", ROLE.REMCTRL);
        mPackList.put("com.busap.httpdatasync", ROLE.HTTPSYNC);
        mPackList.put("com.busap.datasync", ROLE.USBSYNC);
        mPackList.put("com.busap.player", ROLE.PLAYER);
    }

    public AbsSession(Context c) {
        int iRet = -1;
        mCtx = c;

        sessionInit();
    }

    //登录服务端
    public final int Login() {
        int iRet = -1;
        String namePack = mCtx.getPackageName();
        ROLE mRole = mPackList.get(namePack);
        if (mRole == null) {
            throw new RuntimeException(" Login() Excp: " + namePack + " 登录失败");
        }

        return iRet;
    }

    abstract int sessionInit();

    public enum ROLE {
        REMCTRL,
        PLAYER,
        HTTPSYNC,
        TERMINAL,
        USBSYNC,
        MONITOR,

        NOTHING
    }

    public enum ACTION {
        //for http
        HTTP_DOWNLOAD_COMPLETED,
        HTTP_DOWNLOAD_DISABLE,
        HTTP_DOWNLOAD_ENABLE,

        //for login
        LOGIN,

        NOTHIND,
    }
}

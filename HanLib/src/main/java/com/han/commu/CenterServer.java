/**
 * Created by hsx on 17-6-6.
 */
package com.han.commu;

import android.content.Context;

public class CenterServer extends SockServer{
    private static CenterServer mServer = null;

    private Context mCtx = null;

    private CenterServer () {
        super();
    }

    public static CenterServer getCenterServer(Context c) {

        if (!passPrivilegeCensor(c)) {
            throw new RuntimeException(" 包:" + c.getPackageName() + " 不被允许开启此服务");
        }

        if (mServer == null)
            mServer = new CenterServer();

        return mServer;
    }

    @Override
    void handleClientSession(ClientSession cs) {
    }

    @Override
    void handleServerMessage(String s) {

    }
}

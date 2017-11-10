/**
 * Created by hsx on 17-6-6.
 */

package com.busap.commu;

import java.nio.channels.SocketChannel;

public abstract  class AbsSocket {
    private final String    LOCAL_IP     = "127.0.0.1";
    private final int       LOCAL_PORT   = 10020;
    private SocketChannel    mSocketChannel = null;

    public final String getAddr() { return LOCAL_IP; }
    public final int    getPort() { return LOCAL_PORT;}

    public final SocketChannel      getSockChannel() { return mSocketChannel;}
    public final void               setSockChannel(SocketChannel sc) { mSocketChannel = sc;}
}

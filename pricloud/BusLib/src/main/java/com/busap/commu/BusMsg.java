/**
 * Created by hsx on 17-6-6.
 */

package com.busap.commu;

public class BusMsg{
    private AbsSession.ACTION mAction = AbsSession.ACTION.NOTHIND;
    private String mStr = "";

    public static BusMsg getMsg(Byte[] bs) {
        return new BusMsg();
    }

    public Byte[] toBytes() {
        Byte[] bs = new Byte[64];
        return bs;
    }
}

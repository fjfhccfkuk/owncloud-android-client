package com.busap.info;

import android.net.Uri;

/**
 * Created by hsx on 17-7-10.
 */

public class BusURI {
    public final static String BUS_CONT_ROOT_URI = "com.busap.common.BusInfoProvider";

    public final static Uri BUS_URI_QUERY_RETIST =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/queryregist");
    public final static Uri BUS_URI_UPDATE_REGIST =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/updateregist");
    public final static Uri BUS_URI_QUERY_DEVINFO =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/querydevinfo");
    public final static Uri BUS_URI_UPDATE_DEVINFO =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/updatedevinfo");
    public final static Uri BUS_URI_UPDATE_LOCALVOL =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/updatelocalvol");
    public final static Uri BUS_URI_UPDATE_REMOTEVOL =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/updateremotevol");
    public final static Uri BUS_URI_UPDATE_CURVOL =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/updatecurvol");

    public final static Uri BUS_URI_QUERY_LOCALVOL =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/querylocalvol");
    public final static Uri BUS_URI_QUERY_REMOTEVOL =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/queryremotevol");
    public final static Uri BUS_URI_QUERY_CURVOL =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/querycurvol");

    public final static Uri BUS_APPK_URI =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/appk");
    public final static Uri BUS_CITYID_URI =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/cityid");
    public final static Uri BUS_APKVER_URI =Uri.parse("content://" + BUS_CONT_ROOT_URI + "/apkver");

    public final static String BUS_PLAY_CTRL = "http://" + BusIP.getIP(BusIP.IP.IP_PUBLIC_STATION) + "/broadcastControlIssued/transmission/";
    public final static String BUS_PLAY_CTRL_FED = "http://" + BusIP.getIP(BusIP.IP.IP_PUBLIC_STATION) + "/broadcastControlIssued/reported/";
}

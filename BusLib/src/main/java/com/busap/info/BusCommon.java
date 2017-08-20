package com.busap.info;

import android.os.Environment;

/**
 * Created by hsx on 17-6-12.
 */

public class BusCommon {
    public final static String DIR_MEDIA = "//sdcard/CompatibleTrans/media/";
    public final static String MEDIA_BASE_DIR_THIS = Environment.getExternalStorageDirectory().getPath() + "/CompatibleTrans/reserved/";
    public final static String  PLAY_CTL_FLAG = "//sdcard/busap/stop.play";
    public static final String ONOFF_TIME_CHECK =  "//sdcard/CompatibleTrans/cfg";

    public final static int CMD_GET_GPS =   0xa0;
    public final static int CMD_GET_IP  =   0xa1;
}

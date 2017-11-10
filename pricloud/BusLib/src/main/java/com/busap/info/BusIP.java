package com.busap.info;

/**
 * Created by hsx on 17-5-24.
 */

public class BusIP {
    private static final boolean ISPUBLIC = false;

    //test 10.1.254.223 release 10.1.254.222
    private static String  ipPublicSta  =   ((ISPUBLIC)?"10.1.254.222":"10.1.254.223");

    // test = 192.168.109.227:50000, release =  103.10.85.14:45001
    private static String  ipUploadPortalLog = "http://" + ((ISPUBLIC)?"103.10.85.14:45001":"192.168.109.227:50000") + "/log/applog";


    // test=103.10.85.16:21011" release=117.78.52.75:21011
    private static String  ipUploadDevState = "http://" + ((ISPUBLIC)?"103.10.85.15:20030":"103.10.85.16:21011") + "/terminal/";

    private static String ipAdCfg = "http://omtp.busonline.com:10002/ad-service/ad/getAdConfig";

    private static String ipAdData = "http://omtp.busonline.com:10002/ad-service/ad/getAd";

    public enum IP {
        IP_PUBLIC_STATION,      //release station
        IP_TEST_STATION,        //test station
        IP_LOAD_MEDIA,          //下载媒体
        IP_LOAD_PORTAL,         //下载portal
        IP_LOAD_DEV_CFG,        //下载设备配置, e.g 音量配置
        IP_UPLOAD_PORTAL_LOG,   //上载portal日志
        IP_UPLOAD_DEV_STATE,    //上载设备状态, Terminal 中涉及
        IP_WIFI,          //本机wifi ip
        IP_34G,
        IP_BUSAD_CFG,       //广告业务配置url
        IP_BUSAD_DATA,      //广告业务数据 url
    }


    public static String getIP(IP id) {
        String mIP = null;

        switch (id) {
            case IP_LOAD_DEV_CFG:
            case IP_LOAD_MEDIA:
                break;
            case IP_UPLOAD_DEV_STATE:
                mIP = ipUploadDevState;
                break;
            case IP_UPLOAD_PORTAL_LOG:
                mIP = ipUploadPortalLog;//
                break;
            case IP_PUBLIC_STATION:
                mIP = ipPublicSta;
                break;
            case IP_BUSAD_DATA:
                mIP = ipAdData;
                break;
            case IP_BUSAD_CFG:
                mIP = ipAdCfg;
                break;
            default:
                break;
        }

        return mIP;
    }
}

package com.zhhl.android.policemovecareful;

import android.util.Log;

/**
 * Created by miao on 2019/1/8.
 */
public class Api {
    public static int flag = 0;

    public interface __BASED__ {
       String login ="http://192.168.20.230:8081/";
    }

    public static String getIp() {
        Log.e("getIp: ", (ips[flag]));
        return ips[flag];
    }

    static final String[] ips = {
            "http://192.168.20.228:7104",
            "http://192.168.20.228:7081",
            "http://192.168.20.228:7105",
            "http://192.168.20.228:7106",
            "http://192.168.20.228:7107",
            "http://192.168.20.228:7108",
            "http://192.168.20.228:7109",
            "http://192.168.20.228:7112",
            "http://192.168.20.228:7113",
            "http://192.168.20.228:7114"
    };

    public static final int CHANG_CHUN = 0;
    public static final int JI_LIN = 1;
    public static final int SI_PING = 2;
    public static final int GONG_ZHU_LING = 3;
    public static final int LIAO_YUAN = 4;
    public static final int TONG_HUA = 5;
    public static final int MEI_HE_KOU = 6;
    public static final int BAI_SHAN = 7;
    public static final int SONG_YUAN = 8;
    public static final int BAI_CHENG = 9;
    public static final int DEFAULT_CITY = JI_LIN;

}
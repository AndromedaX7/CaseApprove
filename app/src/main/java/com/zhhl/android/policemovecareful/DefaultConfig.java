package com.zhhl.android.policemovecareful;

/**
 * Created by miao on 2018/9/14.
 */
public class DefaultConfig {
    public static final String userId = "C-ZHHLYDSP-001";

    public static String primaryKey(String code) {
        return "MSGTYPE='我的消息'  AND SENDTO = '" + code + "'  order by SENDTODATE desc ";//
//        return  "MSGTYPE='我的消息' order by SENDTODATE desc";
//        return "MSGTYPE='我的消息' AND ID in ('jlostqun-6wdo','j56e770h-gf','j56e8ugn-gh','jloqplmt-6vlu')  ";
    }


    //; = ;//"MSGTYPE='我的消息' AND SENDTO='csmj'";//ID='j56e770h-gf'
//AND  ID in ('jlostqun-6wdo','j56e770h-gf','j56e8ugn-gh','jloqplmt-6vlu')  "
    public static String ip = "http://192.168.20.230:8081/";
    //    public static String ip = "http://10.0.2.2:8080/";
    public final static String path = "/uas/sso/singlesignoncontrol/checkbill.do";

    public static boolean ALL = false;
}

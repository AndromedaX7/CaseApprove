package com.zhhl.android.policemovecareful.bean;

import java.util.ArrayList;

/**
 * Created by miao on 2019/1/14.
 */
public class UserInfoWrapper {

    public String uid;
    public ArrayList<UserInfo> userInfos;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (UserInfo userInfo : userInfos) {
            buff.append(userInfo.getUserName()).append("  ");
        }

        return buff.toString();
    }
}

package com.zhhl.android.policemovecareful.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by miao on 2018/9/26.
 */
public class UserInfo implements Parcelable {

    private String userId;
    private String userName;
    private String unit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfo() {

    }

    public UserInfo(String userId, String userName) {

        this.userId = userId;
        this.userName = userName;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.unit);
    }

    protected UserInfo(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.unit = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}

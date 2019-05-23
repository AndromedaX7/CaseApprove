package com.zhhl.android.policemovecareful.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 案件实体类
 */
public class CaseBasicInformation implements Parcelable {

//    private ArrayList<UserInfo> userInfo;
//
//    public ArrayList<UserInfo> getUserInfo() {
//        return userInfo;
//    }

    private  String users;


    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

//    public void setUserInfo(ArrayList<UserInfo> userInfo) {
//        this.userInfo = userInfo;
//    }

    private String jyaq;

    public String getJyaq() {
        return jyaq;
    }

    public void setJyaq(String jyaq) {
        this.jyaq = jyaq;
    }

    private String state;
    private String name;
    private String type;
    private String admissibleTime;
    private String ajbh;

    public CaseBasicInformation() {
    }

    public String getState() {

        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdmissibleTime() {
        return admissibleTime;
    }

    public void setAdmissibleTime(String admissibleTime) {
        this.admissibleTime = admissibleTime;
    }

    public String getAdmissibler() {
        return admissibler;
    }

    public void setAdmissibler(String admissibler) {
        this.admissibler = admissibler;
    }

    public String getAdmissibleUnit() {
        return admissibleUnit;
    }

    public void setAdmissibleUnit(String admissibleUnit) {
        this.admissibleUnit = admissibleUnit;
    }

    private String admissibler;
    private String admissibleUnit;

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public CaseBasicInformation(String state, String name, String type, String admissibleTime, String ajbh, String admissibler, String admissibleUnit) {
        this.state = state;
        this.name = name;
        this.type = type;
        this.admissibleTime = admissibleTime;
        this.ajbh = ajbh;
        this.admissibler = admissibler;
        this.admissibleUnit = admissibleUnit;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.users);
        dest.writeString(this.jyaq);
        dest.writeString(this.state);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.admissibleTime);
        dest.writeString(this.ajbh);
        dest.writeString(this.admissibler);
        dest.writeString(this.admissibleUnit);
    }

    protected CaseBasicInformation(Parcel in) {
        this.users = in.readString();
        this.jyaq = in.readString();
        this.state = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.admissibleTime = in.readString();
        this.ajbh = in.readString();
        this.admissibler = in.readString();
        this.admissibleUnit = in.readString();
    }

    public static final Parcelable.Creator<CaseBasicInformation> CREATOR = new Parcelable.Creator<CaseBasicInformation>() {
        @Override
        public CaseBasicInformation createFromParcel(Parcel source) {
            return new CaseBasicInformation(source);
        }

        @Override
        public CaseBasicInformation[] newArray(int size) {
            return new CaseBasicInformation[size];
        }
    };
}

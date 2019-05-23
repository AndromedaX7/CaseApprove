package com.zhhl.android.policemovecareful.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QueryData implements Parcelable {
    private String ID;
    private String TOPIC;
    private String CONTENT;
    private String URL;
    private String MSGTYPE;
    private String MSGSTATE;
    private String DEVTYPE;
    private String SENDTO;
    private String SENDFROM;
    private String SENDTODATE;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTOPIC() {
        return TOPIC;
    }

    public void setTOPIC(String TOPIC) {
        this.TOPIC = TOPIC;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMSGTYPE() {
        return MSGTYPE;
    }

    public void setMSGTYPE(String MSGTYPE) {
        this.MSGTYPE = MSGTYPE;
    }

    public String getMSGSTATE() {
        return MSGSTATE;
    }

    public void setMSGSTATE(String MSGSTATE) {
        this.MSGSTATE = MSGSTATE;
    }

    public String getDEVTYPE() {
        return DEVTYPE;
    }

    public void setDEVTYPE(String DEVTYPE) {
        this.DEVTYPE = DEVTYPE;
    }

    public String getSENDTO() {
        return SENDTO;
    }

    public void setSENDTO(String SENDTO) {
        this.SENDTO = SENDTO;
    }

    public String getSENDFROM() {
        return SENDFROM;
    }

    public void setSENDFROM(String SENDFROM) {
        this.SENDFROM = SENDFROM;
    }

    public String getSENDTODATE() {
        return SENDTODATE;
    }

    public void setSENDTODATE(String SENDTODATE) {
        this.SENDTODATE = SENDTODATE;
    }

    public QueryData() {

    }

    public QueryData(String ID, String TOPIC, String CONTENT, String URL, String MSGTYPE, String MSGSTATE, String DEVTYPE, String SENDTO, String SENDFROM, String SENDTODATE) {

        this.ID = ID;
        this.TOPIC = TOPIC;
        this.CONTENT = CONTENT;
        this.URL = URL;
        this.MSGTYPE = MSGTYPE;
        this.MSGSTATE = MSGSTATE;
        this.DEVTYPE = DEVTYPE;
        this.SENDTO = SENDTO;
        this.SENDFROM = SENDFROM;
        this.SENDTODATE = SENDTODATE;
    }

    @Override
    public String toString() {
        return getTOPIC();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.TOPIC);
        dest.writeString(this.CONTENT);
        dest.writeString(this.URL);
        dest.writeString(this.MSGTYPE);
        dest.writeString(this.MSGSTATE);
        dest.writeString(this.DEVTYPE);
        dest.writeString(this.SENDTO);
        dest.writeString(this.SENDFROM);
        dest.writeString(this.SENDTODATE);
    }

    protected QueryData(Parcel in) {
        this.ID = in.readString();
        this.TOPIC = in.readString();
        this.CONTENT = in.readString();
        this.URL = in.readString();
        this.MSGTYPE = in.readString();
        this.MSGSTATE = in.readString();
        this.DEVTYPE = in.readString();
        this.SENDTO = in.readString();
        this.SENDFROM = in.readString();
        this.SENDTODATE = in.readString();
    }

    public static final Parcelable.Creator<QueryData> CREATOR = new Parcelable.Creator<QueryData>() {
        @Override
        public QueryData createFromParcel(Parcel source) {
            return new QueryData(source);
        }

        @Override
        public QueryData[] newArray(int size) {
            return new QueryData[size];
        }
    };
}

package com.zhhl.android.policemovecareful.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by miao on 2019/1/11.
 */
public class QueryDataWrap implements Parcelable {
    public int ipFlag;
    public QueryData data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ipFlag);
        dest.writeParcelable(this.data, flags);
    }

    public QueryDataWrap() {
    }

    protected QueryDataWrap(Parcel in) {
        this.ipFlag = in.readInt();
        this.data = in.readParcelable(QueryData.class.getClassLoader());
    }

    public static final Parcelable.Creator<QueryDataWrap> CREATOR = new Parcelable.Creator<QueryDataWrap>() {
        @Override
        public QueryDataWrap createFromParcel(Parcel source) {
            return new QueryDataWrap(source);
        }

        @Override
        public QueryDataWrap[] newArray(int size) {
            return new QueryDataWrap[size];
        }
    };
}

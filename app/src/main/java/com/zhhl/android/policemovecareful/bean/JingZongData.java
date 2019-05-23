package com.zhhl.android.policemovecareful.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by miao on 2018/11/12.
 */
public class JingZongData {

    @XStreamAlias("Value")
    public static class Values {
        @XStreamImplicit(itemFieldName = "ROW")
        ArrayList<Row> row;

        public ArrayList<Row> getRow() {
            return row;
        }

        public void setRow(ArrayList<Row> row) {
            this.row = row;
        }
    }


    @XStreamAlias("ROW")
    public static class Row {
        @XStreamImplicit(itemFieldName = "Data")
        ArrayList<String> data;

        public ArrayList<String> getData() {
            return data;
        }

        public void setData(ArrayList<String> data) {
            this.data = data;
        }
    }
}

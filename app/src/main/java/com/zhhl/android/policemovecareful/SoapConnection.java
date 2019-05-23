package com.zhhl.android.policemovecareful;

import com.zhhl.android.policemovecareful.bean.QueryData;

import java.util.ArrayList;

/**
 * Created by miao on 2018/9/14.
 */
public interface SoapConnection {

    ArrayList<QueryData> queryGzl(String xml);

    ArrayList<String> queryWenShuBianHao(String xml);

    boolean update(String xml);

    String queryWenShuNeiRong(String wenShuNeiRong);

    String queryXingZhengChuFaBaoGaoShu(String s);

    String queryAnJianJiBenXinXi(String xml);

    String queryUser(String xml);

    String queryApprovalInfo(String xml);

    ArrayList<String> queryDirectory(String name, String admissibleUnit);

    int queryStatistics(String state, String start, String end);
}

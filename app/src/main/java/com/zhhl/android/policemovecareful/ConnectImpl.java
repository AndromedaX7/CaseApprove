package com.zhhl.android.policemovecareful;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.thoughtworks.xstream.XStream;
import com.zhhl.android.policemovecareful.base.App;
import com.zhhl.android.policemovecareful.bean.ApprovalInfo;
import com.zhhl.android.policemovecareful.bean.CaseBasicInformation;
import com.zhhl.android.policemovecareful.bean.JingZongData;
import com.zhhl.android.policemovecareful.bean.QueryData;
import com.zhhl.android.policemovecareful.bean.UpdateStruct;
import com.zhhl.android.policemovecareful.bean.UserInfo;
import com.zhhl.android.policemovecareful.utils.ILogUploadImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.zhhl.android.policemovecareful.HttpTools.execute;

public class ConnectImpl {


    public static XStream xStream;





    static {
        xStream = new XStream();
        xStream.ignoreUnknownElements();
        XStream.setupDefaultSecurity(xStream);
        xStream.autodetectAnnotations(true);
        xStream.allowTypes(new Class[]{JingZongData.Values.class});
        xStream.processAnnotations(JingZongData.Values.class);
    }

    private static String parse(String src) {
        src = src.replaceAll("&quot", "\"").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&#x", "%u").replaceAll(";", "");
        String[] split = src.split("%u");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(split[0]);
        for (int i = 1; i < split.length; i++) {
            char c = (char) Integer.parseInt(split[i].substring(0, 4), 16);
            stringBuilder.append(c);
            if (split[i].length() > 4) {
                stringBuilder.append(split[i].substring(4));
            }
        }
        App.app().getLogReport().log("{}");
        return stringBuilder.toString();
    }

    public ArrayList<QueryData> queryGzl(String condition) {
        String content = content(parse(execute(SoapContent.GZL(condition))));
        Log.e( "queryGzl: ",content);
        JingZongData.Values o = (JingZongData.Values) xStream.fromXML(content);
        App.app().getLogReport().log("{}");
        return parseGZL(o);
    }

    public ArrayList<String> queryWenShuBianHao(String id) {
        JingZongData.Values o = (JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.WSBH(id)))));
        App.app().getLogReport().log("{}");
        return parseWSBH(o);
    }


    public boolean update(String source, String newTopic) {
        XStream xStream = new XStream();
        xStream.ignoreUnknownElements();
        XStream.setupDefaultSecurity(xStream);
        xStream.autodetectAnnotations(true);
        xStream.allowTypes(new Class[]{UpdateStruct.class});
        xStream.processAnnotations(UpdateStruct.class);
        UpdateStruct struct = (UpdateStruct) xStream.fromXML(updateContent(parse(execute(SoapContent.APPROVE(source, newTopic)))));
        App.app().getLogReport().log("{}");
        return !struct.FLAG;
    }

    public String queryWenShuNeiRong(ArrayList<String> wsbhs) {
        JingZongData.Values o = (JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.WSNR(wsbhs)))));
        App.app().getLogReport().log("{}");
        return o.getRow().get(1).getData().get(0);
    }

    public String queryWenShuNeiRongCount(ArrayList<String> wsbhs) {
        JingZongData.Values o = (JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.WSNR_COUNT(wsbhs)))));
        App.app().getLogReport().log("{}");
        return o.getRow().get(1).getData().get(0);
    }

    public String queryXingZhengChuFaBaoGaoShu(String id) {
        JingZongData.Values o = (JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.XZCFBGS(id)))));
        App.app().getLogReport().log("{}");
        return o.getRow().get(1).getData().get(0);
    }

    public String queryXingZhengChuFaBaoGaoShuCount(String id) {
        JingZongData.Values o = (JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.XZCFBGS_COUNT(id)))));
        App.app().getLogReport().log("{}");
        return o.getRow().get(1).getData().get(0);
    }

    public ArrayList<CaseBasicInformation> queryAnJianJiBenXinXi(String startDate, String endDate, String
            anJianZhuangTai, String anJianMingCheng, int pager) {
        JingZongData.Values values = (JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.AJJBXX(startDate, endDate, anJianMingCheng, anJianZhuangTai, pager)))));
        App.app().getLogReport().log("{}");
        return parseAJJBXX(values);
    }

    public ArrayList<UserInfo> queryUserInfo(String code) {
        App.app().getLogReport().log("{}");
        return parseUser((JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.user(code))))));
    }

    public ArrayList<ApprovalInfo> queryApprovalInfos(String ajbh) {

        App.app().getLogReport().log("{}");
        return parseApproveInfo((JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.APPROVE_INFO(ajbh))))));
    }

    public ArrayList<String> queryDirectory(String name, String dirCode) {
        App.app().getLogReport().log("{}");
        return parseDir((JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.DIRECTORY(name, dirCode))))));
    }

    private ArrayList<String> parseDir(JingZongData.Values content) {
        ArrayList<String> dst = new ArrayList<>();
        for (int i = 1; i < content.getRow().size(); i++) {
            dst.add(content.getRow().get(i).getData().get(0));
        }
        return dst;
    }

    public int queryStatistics(String state, String start, String end) {
        App.app().getLogReport().log("{}");
        JingZongData.Values values = (JingZongData.Values) xStream.fromXML(content(parse(execute(SoapContent.STATISTICS(state, start, end)))));
        return Integer.parseInt(values.getRow().get(1).getData().get(0));

    }

    public static String deleteImg(String source) {
        int start = source.indexOf("<IMG");
        String delete = source.substring(start);
        int deleteEnd = delete.indexOf(">");
        String part0 = source.substring(0, start);
        String part1 = source.substring(start + deleteEnd + 1);
        return part0 + part1;
    }

    public static String deleteText(String source, String del) {

//        System.out.println("source:"+source);
        int start = source.indexOf(del);
        int len = del.length();

        if (start != -1) {
            String part0 = source.substring(0, start);
            System.out.println(part0);
            String part1 = source.substring(start + len);

            return part0 + part1;
        } else {
            return source;
        }
    }

    public static String getNoImg(String source) {
        byte[] decode = Base64.decode(source.getBytes(), Base64.DEFAULT);
        String dst = null;
        try {
            dst = new String(decode, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        while (findImg(dst)) {
            dst = deleteImg(dst);
        }
        return dst;
    }

    public static boolean findImg(String source) {
        return source.contains("<IMG");
    }

    public String content(String src) {
        int start = src.indexOf("<Value");
        int end = src.indexOf("</Item>");
        return src.substring(start, end);
    }

    public String updateContent(String src) {
        int start = src.indexOf("<Error>");
        int end = src.indexOf("</Error>");

        String substring = src.substring(start, end + "</Error>".length());
        Log.e("update", substring);

        return substring;
    }

    private ArrayList<QueryData> parseGZL(JingZongData.Values data) {
        ArrayList<QueryData> dst = new ArrayList<>();
        for (int i = 1; i < data.getRow().size(); i++) {
            ArrayList<String> _data = data.getRow().get(i).getData();
            QueryData queryData = new QueryData();
            queryData.setID(_data.get(0));
            queryData.setTOPIC(_data.get(1));
            queryData.setCONTENT(_data.get(2));
            queryData.setMSGTYPE(_data.get(3));
            queryData.setMSGSTATE(_data.get(4));
            queryData.setDEVTYPE(_data.get(5));
            queryData.setSENDTO(_data.get(6));
            queryData.setSENDFROM(_data.get(7));
            queryData.setSENDTODATE(_data.get(8));
            dst.add(queryData);
        }
        return dst;
    }

    private ArrayList<String> parseWSBH(JingZongData.Values data) {
        ArrayList<String> wsbhs = new ArrayList<>();
        for (int i = 1; i < data.getRow().size(); i++) {
            wsbhs.add(data.getRow().get(i).getData().get(0));
        }
        return wsbhs;
    }

    private ArrayList<CaseBasicInformation> parseAJJBXX(JingZongData.Values data) {
        ArrayList<CaseBasicInformation> dst = new ArrayList<>();
        for (int i = 1; i < data.getRow().size(); i++) {
            ArrayList<String> caseInfo = data.getRow().get(i).getData();
            CaseBasicInformation c = new CaseBasicInformation();
            c.setName(caseInfo.get(0));
            c.setType(caseInfo.get(1));
            c.setAdmissibler(caseInfo.get(2));
            c.setAdmissibleTime(caseInfo.get(3));
            c.setAdmissibleUnit(caseInfo.get(4));
            c.setAjbh(caseInfo.get(5));
            c.setState(caseInfo.get(6));
            c.setJyaq(caseInfo.get(7));
            dst.add(c);
        }
        return dst;
    }

    private ArrayList<UserInfo> parseUser(JingZongData.Values data) {
        ArrayList<UserInfo> dst = new ArrayList<>();
        for (int i = 1; i < data.getRow().size(); i++) {
            ArrayList<String> usr = data.getRow().get(i).getData();

            UserInfo information = new UserInfo();
            information.setUserId(usr.get(0));
            information.setUserName(usr.get(1));
            information.setUnit(usr.get(2));
            dst.add(information);
        }
        return dst;
    }

    private ArrayList<ApprovalInfo> parseApproveInfo(JingZongData.Values data) {
        ArrayList<ApprovalInfo> dst = new ArrayList<>();
        for (int i = 1; i < data.getRow().size(); i++) {
            ArrayList<String> _data = data.getRow().get(i).getData();

            ApprovalInfo information = new ApprovalInfo();
            information.setFLYJ(_data.get(0));
            information.setSPJG(_data.get(1));
            information.setSPYJ(_data.get(2));
            information.setSPRQ(_data.get(3));
            dst.add(information);
        }
        return dst;
    }

    public static String filePath(Context context, String bytes) {
        File dir = new File(context.getFilesDir().getAbsolutePath() + "/html");
        if (!dir.exists()) dir.mkdirs();
        File current = new File(dir, System.currentTimeMillis() + ".html");
        try {
            FileOutputStream out = new FileOutputStream(current);
            out.write(bytes.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return current.getAbsolutePath();
    }

    public static String updateText(String sub, String source) {
        String[] split = source.split("`");
        return split[0] + "`" + sub + split[1];
    }
}

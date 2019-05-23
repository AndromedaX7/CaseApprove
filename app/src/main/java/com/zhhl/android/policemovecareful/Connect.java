//package com.zhhl.android.policemovecareful;
//
//import android.content.Context;
//import android.util.Base64;
//import android.util.Log;
//
//import com.zhhl.android.policemovecareful.bean.ApprovalInfo;
//import com.zhhl.android.policemovecareful.bean.CaseBasicInformation;
//import com.zhhl.android.policemovecareful.bean.QueryData;
//import com.zhhl.android.policemovecareful.bean.UserInfo;
//
//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.PropertyInfo;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;
//import org.kxml2.io.KXmlParser;
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Vector;
//
//
//public class Connect implements SoapConnection {
//
//    public static SoapObject create(String blob, String nameSpaceAddress, String methodNameAddress, String urlAddress, String soapActionAddress) {
//        SoapObject soapObject = new
//                SoapObject(nameSpaceAddress, methodNameAddress);//创建SOAP对象
//        //设置属性，这些属性值通过SOAP协议传送给服务器
//        soapObject.addProperty("Blob", /*new  String (Base64.getEncoder().encode*/blob);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                SoapEnvelope.VER11);
//        envelope.bodyOut = soapObject;
//        envelope.dotNet = true;
//        envelope.setOutputSoapObject(soapObject);
//        HttpTransportSE httpTransportSE = new HttpTransportSE(urlAddress);
//        try {
//            //调用服务
//            httpTransportSE.call(soapActionAddress, envelope);
//            Object response = envelope.getResponse();
////            new Handler(Looper.getMainLooper()).post(() -> {
////                Toast.makeText(App.app(),methodNameAddress+"\n"+response,Toast.LENGTH_SHORT).show();
////            });
//            return (SoapObject) envelope.bodyIn;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public static String toString(SoapObject soapObject) {
//        if (soapObject == null) return "";
//        Field propertiesField;
//
//        Vector properties = null;
//        try {
//            propertiesField = SoapObject.class.getDeclaredField("properties");
//            propertiesField.setAccessible(true);
//            properties = (Vector) propertiesField.get(soapObject);
//
//            StringBuilder buf = new StringBuilder();
//            for (int i = 0; i < soapObject.getPropertyCount(); ++i) {
//
//                Object prop = properties.elementAt(i);
//                if (prop instanceof PropertyInfo) {
//                    buf.append("").append(((PropertyInfo) prop).getName()).append("=").append(soapObject.getProperty(i)).append("; ");
//                } else {
//                    buf.append(prop.toString());
//                }
//            }
//            String string = buf.toString();
//            int index = string.indexOf("<");
//            int last = string.lastIndexOf(">");
//
//            return string.substring(index, last + 1);
//
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return "";
//    }
//
//    public static ArrayList<QueryData> parseQuery(String xml, ArrayList<String> args) {
//        ArrayList<String> datas = parseCore(xml, args);
//        int size = args.size();
//        QueryData d = null;
//        ArrayList<QueryData> qData = new ArrayList<>();
//        for (int i = 0; i < datas.size(); i++) {
//            String data = datas.get(i);
//            switch (i % size) {
//                case 0:
//                    d = new QueryData();
//                    d.setID(data);
//                    break;
//                case 1:
//                    d.setTOPIC(data);
//                    break;
//                case 2:
//                    d.setCONTENT(data);
//                    break;
//                case 3:
//                    d.setURL(data);
//                    break;
//                case 4:
//                    d.setMSGTYPE(data);
//                    break;
//                case 5:
//                    d.setMSGSTATE(data);
//                    break;
//                case 6:
//                    d.setDEVTYPE(data);
//                    break;
//                case 7:
//                    d.setSENDTO(data);
//                    break;
//                case 8:
//                    d.setSENDFROM(data);
//                    break;
//                case 9:
//                    d.setSENDTODATE(data);
//                    qData.add(d);
//                    break;
//                default:
//            }
//        }
//        return qData;
//    }
//
//
//    public static boolean commitSuccess(String xml) {
//        XmlPullParser pullParser = new KXmlParser();
//        try {
//            pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
//            pullParser.setInput(new InputStreamReader(new ByteArrayInputStream(xml.getBytes())));
//            int eventType = pullParser.getEventType();
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//                        if ("Flag".equals(pullParser.getName())) {
//                            return !Boolean.valueOf(pullParser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                }
//                eventType = pullParser.next();
//            }
//        } catch (XmlPullParserException | IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    @Override
//    public ArrayList<QueryData> queryGzl(String xml) {
//        SoapObject soapObject = Connect.create(xml, nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        System.out.println(Connect.toString(soapObject));
//        ArrayList<String> listArgs = new ArrayList<>();
//        listArgs.add("ID");
//        listArgs.add("TOPIC");
//        listArgs.add("CONTENT");
//        listArgs.add("URL");
//        listArgs.add("MSGTYPE");
//        listArgs.add("MSGSTATE");
//        listArgs.add("DEVTYPE");
//        listArgs.add("SENDTO");
//        listArgs.add("SENDFROM");
//        listArgs.add("SENDTODATE");
//        return Connect.parseQuery(Connect.toString(soapObject), listArgs);
//    }
//
//    @Override
//    public ArrayList<String> queryWenShuBianHao(String xml) {
//        SoapObject soapObject = Connect.create(xml, nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        String string = Connect.toString(soapObject);
//        Log.e("call", "queryWenShuBianHao: " + string);
//        ArrayList<String> listArgs2 = new ArrayList<>();
//        listArgs2.add("A-SADJB-001.WSBH");
//        ArrayList<String> list = Connect.parseQueryWenShuBianHao(string, listArgs2);
//        return list;
//    }
//
//    @Override
//    public boolean update(String xml) {
//        SoapObject soapObject2 = Connect.create(xml, nameSpaceAddress, methodReport, urlAddress(), reportActionAddress);
//        System.out.println(Connect.toString(soapObject2));
//        return Connect.commitSuccess(Connect.toString(soapObject2));
//    }
//
//    @Override
//    public String queryWenShuNeiRong(String wenShuNeiRong) {
//        SoapObject soapObject = Connect.create(wenShuNeiRong, nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        String string = Connect.toString(soapObject);
//        ArrayList<String> a = new ArrayList<>();
//        a.add("WSNR");
//        ArrayList<String> blob = Connect.parseQueryWenShuBianHao(string, a);
//        Log.e("queryWenShuNeiRong: ", string);
//        if (blob.size() > 0)
//            return blob.get(0);
//        else return "";
//    }
//
//    @Override
//    public String queryXingZhengChuFaBaoGaoShu(String xml) {
//        ArrayList<String> ada = new ArrayList<>();
//        ada.add("A-FLWS-001.WSNR");
//        SoapObject soapObject = Connect.create(xml, nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        String string = Connect.toString(soapObject);
//        ArrayList<String> blob = Connect.parseQueryWenShuBianHao(string, ada);
//        if (blob.size() == 0) return "";
//        Log.e("queryXingZhengCFBGS: ", string);
//        return blob.get(0);
//    }
//
//    @Override
//    public String queryAnJianJiBenXinXi(String xml) {
//        SoapObject soapObject = Connect.create(xml, nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        String string = Connect.toString(soapObject);
//        Log.e("queryAnJianJiBenXinXi: ", string);
//        return string;
//    }
//
//    @Override
//    public String queryUser(String xml) {
//        SoapObject soapObject = Connect.create(xml, nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        String string = Connect.toString(soapObject);
//        Log.e("queryUser: ", string);
//        return string;
//    }
//
//    @Override
//    public String queryApprovalInfo(String xml) {
//        SoapObject soapObject = Connect.create(xml, nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        String string = Connect.toString(soapObject);
//        Log.e("queryApprovalInfo: ", string);
//        return string;
//    }
//
//    @Override
//    public ArrayList<String> queryDirectory(String name, String admissibleUnit) {
//
//        SoapObject soapObject = Connect.create(XmlFactory.queryDirectory(name, admissibleUnit), nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        String string = Connect.toString(soapObject);
//
//        ArrayList<String> args = new ArrayList<>();
//        args.add("ZDZ");
////        Log.e("queryDirectory: ", string);
//        return parseCore(string, args);
//    }
//
//    @Override
//    public int queryStatistics(String state, String start, String end) {
//        SoapObject soapObject = Connect.create(XmlFactory.queryStatistics(state, start, end), nameSpaceAddress, methodQuery, urlAddress(), queryActionAddress);
//        String string = Connect.toString(soapObject);
//        ArrayList<String> a = new ArrayList<>();
//        a.add("count(*)");
//
//        ArrayList<String> strings = parseCore(string, a);
//        Log.e("queryStatistics: ", string);
//        return Integer.parseInt(strings.size() == 0 ? "0" : strings.get(0));
//    }
//
//    public static ArrayList<UserInfo> parseUser(String xml) {
//        ArrayList<String> args = new ArrayList<>();
//        args.add("YHM");
//        args.add("YHXM");
//        args.add("DWDM");
//        ArrayList<String> result = parseCore(xml, args);
//
//        int size = args.size();
//        UserInfo information = null;
//        ArrayList<UserInfo> qData = new ArrayList<>();
//        for (int i = 0; i < result.size(); i++) {
//            String data = result.get(i);
//            switch (i % size) {
//                case 0:
//                    information = new UserInfo();
//                    information.setUserId(data);
//                    break;
//                case 1:
//                    information.setUserName(data);
//                    break;
//                case 2:
//                    information.setUnit(data);
//                    qData.add(information);
//                default:
//            }
//        }
//        return qData;
//
//    }
//
//
//    public static ArrayList<CaseBasicInformation> parseBasicInfo(String xml) {
//
//        ArrayList<String> info = new ArrayList<>();
//        info.add("A-AJJBXX-001.AJMC");
//        info.add("A-AJJBXX-001.AJLB");
//        info.add("A-AJJBXX-001.SLR");
//        info.add("A-AJJBXX-001.LARQ");
//        info.add("A-AJJBXX-001.SLDW");
//        info.add("A-AJJBXX-001.AJBH");
//        info.add("Z-ZDB-001.ZDZ");
//        info.add("A-AJJBXX-001.AQZS");
//        ArrayList<String> result = parseCore(xml, info);
//        int size = info.size();
//        CaseBasicInformation information = null;
//        ArrayList<CaseBasicInformation> qData = new ArrayList<>();
//        for (int i = 0; i < result.size(); i++) {
//            String data = result.get(i);
//            switch (i % size) {
//                case 0:
//                    information = new CaseBasicInformation();
//                    information.setName(data);
//                    break;
//                case 1:
//                    information.setType(data);
//                    break;
//                case 2:
//                    information.setAdmissibler(data);
//                    break;
//                case 3:
//                    information.setAdmissibleTime(data);
//                    break;
//                case 4:
//                    information.setAdmissibleUnit(data);
//
//                    break;
//                case 5:
//                    information.setAjbh(data);
//                    break;
//                case 6:
//                    information.setState(data);
//                    break;
//                case 7:
//                    information.setJyaq(data);
//                    qData.add(information);
//                    break;
//                default:
//            }
//        }
//        return qData;
//
//
//    }
//
//
//    public static ArrayList<ApprovalInfo> parseApprovalInfo(String xml) {
//        ArrayList<String> args = new ArrayList<>();
//        args.add("FLYJ");
//        args.add("SPJG");
//        args.add("SPYJ");
//        args.add("SPRQ");
//        ArrayList<String> result = parseCore(xml, args);
//
//        int size = args.size();
//        ApprovalInfo information = null;
//        ArrayList<ApprovalInfo> qData = new ArrayList<>();
//        for (int i = 0; i < result.size(); i++) {
//            String data = result.get(i);
//            switch (i % size) {
//                case 0:
//                    information = new ApprovalInfo();
//                    information.setFLYJ(data);
//                    break;
//                case 1:
//                    information.setSPJG(data);
//                    break;
//                case 2:
//                    information.setSPYJ(data);
//                    break;
//                case 3:
//                    information.setSPRQ(data);
//                    qData.add(information);
//                default:
//            }
//        }
//
//        return qData;
//    }
//
//    public static String updateText(String sub, String source) {
//        String[] split = source.split("`");
//        return split[0] + "`" + sub + split[1];
//    }
//
//
//    public static ArrayList<String> parseQueryWenShuBianHao(String xml, ArrayList<String> args) {
//        return parseCore(xml, args);
//    }
//
//    private static ArrayList<String> parseCore(String xml, ArrayList<String> args) {
//        ArrayList<String> datas = new ArrayList<>();
//        XmlPullParser pullParser = new KXmlParser();
//        try {
//            pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
//            pullParser.setInput(new InputStreamReader(new ByteArrayInputStream(xml.getBytes())));
//            int eventType = pullParser.getEventType();
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//
//                        if ("Data".equals(pullParser.getName())) {
//                            String textContent = pullParser.nextText();
//                            if (!(args.contains(textContent))) {
//
//                                textContent = textContent == null ? "" : textContent;
//                                datas.add(textContent);
//                            }
//
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                }
//                eventType = pullParser.next();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return datas;
//    }
//
//    public static String decodeBase64(Context context, String blob) {
//        if (blob.indexOf("=") == (blob.length() - 1) || blob.indexOf("=") == (blob.length() - 2)) {
//            blob = blob.substring(0, blob.indexOf("="));
//        }
//        byte[] decode = Base64.decode(blob, Base64.DEFAULT);
//        try {
//
//            File dir = new File(context.getFilesDir().getAbsolutePath() + "/html");
//            if (!dir.exists()) dir.mkdirs();
//            File current = new File(dir, System.currentTimeMillis() + ".html");
//            FileOutputStream out = new FileOutputStream(current);
//            out.write(decode);
//            out.flush();
//            out.close();
//            return current.getAbsolutePath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//    private static final String nameSpaceAddress = "http://server.tcwebservice.tc.com";
//
//    private static final String urlAddress() {
//        return Api.getIp() + "/tcwebservice/services/ServiceImpl?wsdl";
//    }
//
//    public static final String urlAddressServer() {
//        return Api.getIp();
//    }
//
//    private static final String methodQuery = "QueryService";
//    private static final String queryActionAddress = "http://server.tcwebservice.tc.com/QueryService";
//
//    private static final String methodReport = "ReportService";
//    private static final String reportActionAddress = "http://server.tcwebservice.tc.com/ReportService";
//}

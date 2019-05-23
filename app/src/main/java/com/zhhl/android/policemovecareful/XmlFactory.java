package com.zhhl.android.policemovecareful;

import java.util.ArrayList;


/**
 * Created by miao on 2018/9/14.
 */
public class XmlFactory {

    public static String queryGzl(String userId, String tableName, String where) {
        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n"
                + userId +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryServiceRequest\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\"\"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>" + tableName + "</Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        <![CDATA[" + where + "]]>\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>1</Data>\n" +
                "                        <Data>20</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>ID</Data>\n" +
                "                        <Data>TOPIC</Data>\n" +
                "                        <Data>CONTENT</Data>\n" +
                "                        <Data>URL</Data>\n" +
                "                        <Data>MSGTYPE</Data>\n" +
                "                        <Data>MSGSTATE</Data>\n" +
                "                        <Data>DEVTYPE</Data>\n" +
                "                        <Data>SENDTO</Data>\n" +
                "                        <Data>SENDFROM</Data>\n" +
                "                        <Data>SENDTODATE</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";


    }


    public static String queryWenShuBianHao(String userId, String ID) {
        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n" +
                userId +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryService\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\" \"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>" +
                "                        C-GZL-001,A-SPXX-001,A-SADJB-001,A-CQBGS-001" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "<![CDATA[\n" +
                "A-SPXX-001.AJSPWSBH = substr(trim(C-GZL-001.TOPIC),1,23) and C-GZL-001.ID='" + ID + "' and A-SADJB-001.CZBS<'3' and A-SPXX-001.CZBS<'3' and A-SPXX-001.AJBH = A-SADJB-001.AJBH" + " and ( A-SPXX-001.AJSPWSBH = A-CQBGS-001.ZJ OR A-SPXX-001.AJSPWSBH = A-SADJB-001.ZJ)" +
                "]]>\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>1</Data>\n" +
                "                        <Data>10</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>A-SADJB-001.WSBH</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";

    }


    public static String uploadXml(String userId, String tablename, String source, String
            newTopic) {
        return "<?xml version=\"1.0\" encoding=\"GB2312\" ?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n"
                + userId +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryServiceRequest\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\"\"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    Recorders\n" +
                "                </Name>\n" +
                "                <Value Type=\"arrayOf_string\">\n" +
                "                    <Row state=\"u\" tablename=\"" + tablename + "\">\n" +
                "                        <Data TOPIC=\"" + newTopic + "\">\n" +
                "\n" +
                "                        </Data>\n" +
                "                        <SqlWhere>\n" +
                "                            <![CDATA[\n" +
                "                                TOPIC='" + source + "'\n" +
                "                            ]]>\n" +
                "                        </SqlWhere>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "                </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }

    public static String result() {
        return "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>C-ZHHLYDSP-001</RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>Query</Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "            <Encrypt Algorithm=\"\"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Error>\n" +
                "                    <Flag>false</Flag>\n" +
                "                    <ErrID/>\n" +
                "                    <Msg/>\n" +
                "                </Error>\n" +
                "                <Value Type=\"arrayOfArrayOf_string\">\n" +
                "                    <ROW>\n" +
                "                        <Data>ID</Data>\n" +
                "                        <Data>TOPIC</Data>\n" +
                "\n" +
                "                        <Data>CONTENT</Data>\n" +
                "                        <Data>URL</Data>\n" +
                "\n" +
                "                        <Data>MSGTYPE</Data>\n" +
                "                        <Data>MSGSTATE</Data>\n" +
                "\n" +
                "                        <Data>DEVTYPE</Data>\n" +
                "                        <Data>SENDTO</Data>\n" +
                "\n" +
                "                        <Data>SENDFROM</Data>\n" +
                "                        <Data>SENDTODATE</Data>\n" +
                "                    </ROW>\n" +
                "                    <ROW>\n" +
                "                        <Data>id-0000</Data>\n" +
                "                        <Data>SEQ22020020170400129600`2017年04月20日【吉林市背叛国家案】的呈请立案报告书（审批）</Data>\n" +
                "                        <Data>你收到一项新的工作：“一级审批”，流程主题是：“2017年04月20日【吉林市背叛国家案】的呈请立案报告书（审批）” 请你尽快办理。</Data>\n" +
                "                      \n" +
                "                        <Data>/xrap/zhxt/xsaj/spxx/Brwbgssldsp.html?itemID=j56e770h-gf&amp;spzj=SEQ22020020170400129600&amp;cqsx=1201&amp;spjb=01&amp;tjpd=0&amp;spzl=02 &amp;splxzj=SEQ22020020160400001738&amp;zgjb=05</Data>\n" +
                "                        <Data>我的消息</Data>\n" +
                "                        \n" +
                "                        <Data>0</Data>\n" +
                "                        <Data>2</Data>\n" +
                "                        <Data>csmj</Data>\n" +
                "                        <Data>csmj:测试民警</Data>\n" +
                "                        <Data>2017-07-16 15:21:16.0</Data>\n" +
                "                    </ROW>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }

    public static String queryWenShuNeiRong(String userId, ArrayList<String> list) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {

            b.append("'").append(list.get(i)).append("'");
            if (i != list.size() - 1) {
                b.append(",");
            }
        }
        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n" +
                userId +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryService\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\" \"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>A-FLWS-001</Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "<![CDATA[\n" +
                "  A-FLWS-001.FLWSBH in  ( " + b.toString() + ")  and SFYX='1' and CZBS<'3'" +
                "]]>\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>1</Data>\n" +
                "                        <Data>10</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>WSNR</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }

    /**
     * @param userId C-ZHHLYDSP-001
     * @param id     jlostqun-6wdo
     * @return
     */
    public static String queryXingZhengChuFaBaoGaoShu(String userId, String id) {
        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n" +
                userId +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryService\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\" \"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>C-GZL-001,A-FLWS-001,A-CQBGS-001</Data>\n" +//C-GZL-001,A-FLWS-001,
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                     <![CDATA[\n" +
                "                                         A-FLWS-001.flwsbh = A-CQBGS-001.wsbh and  substr(trim(C-GZL-001.topic) ,1,23)=A-CQBGS-001.ZJ and A-CQBGS-001.czbs <'3'  and A-FLWS-001.czbs<'3' and A-FLWS-001.sfyx='1' and C-GZL-001.ID='" + id + "'\n" +
                "                     ]]>\n" +
                "\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>1</Data>\n" +
                "                        <Data>1</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>A-FLWS-001.WSNR</Data>\n" +
                "\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }


    public static String queryAnJianJiBenXinXi(String startDate, String endDate, String
            anJianZhuangTai, String anJianMingCheng, int pager) {
        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n" +
                "        C-ZHHLYDSP-001\n" +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryServiceRequest\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\" \"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>A-AJJBXX-001,Z-ZDB-001</Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        <![CDATA[\n" +
                "                            LARQ between '" + startDate + "' and  '" + endDate + "'  " + ajmc(anJianMingCheng) + "  and AJZT=Z-ZDB-001.ZDDM and Z-ZDB-001.ZDMC='案件状态' " + ajzt(anJianZhuangTai) + "\n" +
                "                        ]]>\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>" + pager + "</Data>\n" +
                "                        <Data>10</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>A-AJJBXX-001.AJMC</Data>\n" +
                "                        <Data>A-AJJBXX-001.AJLB</Data>\n" +
                "                        <Data>A-AJJBXX-001.SLR</Data>\n" +
                "                        <Data>A-AJJBXX-001.LARQ</Data>\n" +
                "                        <Data>A-AJJBXX-001.SLDW</Data>\n" +
                "                        <Data>A-AJJBXX-001.AJBH</Data>\n" +
                "                        <Data>Z-ZDB-001.ZDZ</Data>\n" +
                "                        <Data>A-AJJBXX-001.AQZS</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }

    public static String queryUser(String userCode) {
        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n" +
                "        C-ZHHLYDSP-001\n" +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryService\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\" \"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>Y-YHB-001</Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "<![CDATA[\n" +
                "  USER_ID='" + userCode + "'  " +
                "]]>\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>1</Data>\n" +
                "                        <Data>10</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>YHM</Data>\n" +
                "                        <Data>YHXM</Data>\n" +
                "                        <Data>DWDM</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }


    private static String ajmc(String ajmc) {
        if (ajmc == null || ajmc.equals("")) return "";
        else return " and A-AJJBXX-001.AJMC like '%" + ajmc + "%'  ";
    }

    private static String ajzt(String ajzt) {
        if (ajzt == null || ajzt.equals("")) return "";
        else return " and Z-ZDB-001.ZDZ='" + ajzt + "'  ";
    }

    public static String queryApprovalInfo(String ajbh) {

        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n" +
                "        C-ZHHLYDSP-001\n" +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryService\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\" \"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>A-SPXX-001</Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "<![CDATA[\n" +
                "   A-SPXX-001.AJBH='" + ajbh + "'" +
                "]]>\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>1</Data>\n" +
                "                        <Data>10</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>FLYJ</Data>\n" +
                "                        <Data>SPJG</Data>\n" +
                "                        <Data>SPYJ</Data>\n" +
                "                        <Data>SPRQ</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }

    public static String queryDirectory(String name, String admissibleUnit) {

        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n" +
                "        C-ZHHLYDSP-001\n" +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryService\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\" \"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>Z-ZDB-001</Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "<![CDATA[\n" +
                "   Z-ZDB-001.ZDMC='" + name + "' and Z-ZDB-001.ZDDM = '" + admissibleUnit + "'" +
                "]]>\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>1</Data>\n" +
                "                        <Data>10</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>ZDZ</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }

    public static String queryStatistics(String state, String start, String end) {
        return "<?xml version= \"1.0\" encoding= \"GB2312\"?>\n" +
                "<RBSPMessage>\n" +
                "    <RequesterID>\n" +
                "        C-ZHHLYDSP-001\n" +
                "    </RequesterID>\n" +
                "    <Method>\n" +
                "        <Name>\n" +
                "            QueryService\n" +
                "        </Name>\n" +
                "        <Security>\n" +
                "            <Signature/>\n" +
                "\n" +
                "            <Encrypt Algorithm=\" \"/>\n" +
                "        </Security>\n" +
                "        <Items>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    SysAlias\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "                        DSjwzhxt\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>\n" +
                "                    DataObjectCode\n" +
                "                </Name>\n" +
                "\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>A-AJJBXX-001,Z-ZDB-001</Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Condition</Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Data>\n" +
                "<![CDATA[\n" +
                "  Z-ZDB-001.ZDMC='案件状态' and Z-ZDB-001.ZDDM=A-AJJBXX-001.AJZT   " + state(state) + date(start, end) +
                "]]>\n" +
                "                    </Data>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>Pager\n" +
                "                </Name>\n" +
                "                <Value Type=\"string \">\n" +
                "                    <Row>\n" +
                "                        <Data>1</Data>\n" +
                "                        <Data>10</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "            <Item>\n" +
                "                <Name>RequiredItems</Name>\n" +
                "                <Value Type=\"arrayOf_string \">\n" +
                "                    <Row>\n" +
                "                        <Data>count(*)</Data>\n" +
                "                    </Row>\n" +
                "                </Value>\n" +
                "            </Item>\n" +
                "        </Items>\n" +
                "    </Method>\n" +
                "</RBSPMessage>";
    }


    private static String state(String state) {
        if (state == null || state.equals("")) return "";
        else return " and Z-ZDB-001.ZDZ='" + state + "' ";
    }

    private static String date(String start, String end) {
        if (start != null && end != null && !start.equals("") && !end.equals(""))
            return " and A-AJJBXX-001.SLSJ between '" + start + "' and '" + end + "'  ";
        else return "";


    }
}

package com.zhhl.android.policemovecareful.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xdja.sslvpn.api.VpnApi50;
import com.xdja.sslvpn.api.VpnApi50Impl;
import com.xdja.uaac.api.UaacApi;
import com.zhhl.android.policemovecareful.Api;
import com.zhhl.android.policemovecareful.ConnectImpl;
import com.zhhl.android.policemovecareful.DefaultConfig;
import com.zhhl.android.policemovecareful.HttpTools;
import com.zhhl.android.policemovecareful.Login;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.StaticMap;
import com.zhhl.android.policemovecareful.base.App;
import com.zhhl.android.policemovecareful.base.BaseActivity;
import com.zhhl.android.policemovecareful.bean.CardInfo;
import com.zhhl.android.policemovecareful.bean.LoginBean;
import com.zhhl.android.policemovecareful.bean.QueryData;
import com.zhhl.android.policemovecareful.bean.QueryDataWrap;
import com.zhhl.android.policemovecareful.utils.ILogUploadImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.zhhl.android.policemovecareful.Api.BAI_CHENG;
import static com.zhhl.android.policemovecareful.Api.BAI_SHAN;
import static com.zhhl.android.policemovecareful.Api.CHANG_CHUN;
import static com.zhhl.android.policemovecareful.Api.DEFAULT_CITY;
import static com.zhhl.android.policemovecareful.Api.GONG_ZHU_LING;
import static com.zhhl.android.policemovecareful.Api.JI_LIN;
import static com.zhhl.android.policemovecareful.Api.LIAO_YUAN;
import static com.zhhl.android.policemovecareful.Api.MEI_HE_KOU;
import static com.zhhl.android.policemovecareful.Api.SI_PING;
import static com.zhhl.android.policemovecareful.Api.SONG_YUAN;
import static com.zhhl.android.policemovecareful.Api.TONG_HUA;
import static com.zhhl.android.policemovecareful.DefaultConfig.primaryKey;

/**
 * Created by yuan on 2018/8/6.
 * 主页
 */
public class MainActivity extends BaseActivity {

    private int flag;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.shenpi)
    ImageView shenpi;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.approval_statistics)
    ImageView approvalStatistics;
    @BindView(R.id.approval_history)
    ImageView approvalHistory;
    @BindView(R.id.query_case)
    ImageView queryCase;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.police)
    TextView police;
    private ArrayList<QueryDataWrap> data = new ArrayList<>();


    ArrayList<QueryDataWrap> pendingApprovalData = new ArrayList<>();
    ArrayList<QueryDataWrap> approvalData = new ArrayList<>();


    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UaacApi.getToken(this, ((App) getApplication()).getCallback());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        setContentView(R.layout.activity_main_v2);
        ButterKnife.bind(this);
        count.setText("0");
        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("正在加载中...");
        dialog.show();
        VpnApi50 vpnApi50 = new VpnApi50Impl(this);
        vpnApi50.start();
        String cardID = vpnApi50.getCardID();
        getCodeInfo(cardID);
        initListener();
    }

    @SuppressLint("CheckResult")
    private void getCodeInfo(String cardID) {
        Observable.create((ObservableOnSubscribe<CardInfo>) e -> {
            CardInfo cardInfo = new Gson().fromJson(codeUserInfo(cardID), CardInfo.class);
            e.onNext(cardInfo);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::codeRes, this::onErr, this::onComplete)
                .isDisposed();
    }

    private void codeRes(CardInfo cardInfo) {
        String depName = cardInfo.getObj().getDepName();
        DefaultConfig.ALL = /*depName.contains("测试机构") ||*/ depName.contains("公安厅");
        Log.e("codeRes: ", depName + DefaultConfig.ALL);
        if (depName.contains("长春市")) {
            Api.flag = CHANG_CHUN;
        } else if (depName.contains("吉林市")) {
            Api.flag = JI_LIN;
        } else if (depName.contains("四平市")) {
            Api.flag = SI_PING;
        } else if (depName.contains("公主岭市")) {
            Api.flag = GONG_ZHU_LING;
        } else if (depName.contains("辽源市")) {
            Api.flag = LIAO_YUAN;
        } else if (depName.contains("通化市")) {
            Api.flag = TONG_HUA;
        } else if (depName.contains("梅河口市")) {
            Api.flag = MEI_HE_KOU;
        } else if (depName.contains("白山市")) {
            Api.flag = BAI_SHAN;
        } else if (depName.contains("松原市")) {
            Api.flag = SONG_YUAN;
        } else if (depName.contains("白城市")) {
            Api.flag = BAI_CHENG;
        } else {
            Api.flag = DEFAULT_CITY;
        }

        Log.e("IpAddr: ", Api.getIp());
    }

    public static String codeUserInfo(String code) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "thridId=xdja&cardNO=" + code);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://192.168.20.228:8180/pams/scms/readcardcontroller/readcardinfo.do")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Cache-Control", "no-cache")
                .build();

        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setUserInfor() {
        queryData();
        LoginBean userInfo = ((App) getApplication()).getUserInfo();
        if (userInfo != null) {
            tv.setText(userInfo.getUserInfo().getName());
            police.setText(StaticMap.sMap.get(Integer.parseInt(userInfo.getUserInfo().getPolice())));
            code.setText(userInfo.getUserInfo().getCode());
            App.app().setLogReport(new ILogUploadImpl(new VpnApi50Impl(this)));
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private void initListener() {
        shenpi.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, PendingActivity.class);
                intent.putParcelableArrayListExtra("approvalData", approvalData);
                intent.putParcelableArrayListExtra("pendingApprovalData", pendingApprovalData);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();

            }
        });
    }

    private void queryData() {
        if (((App) getApplication()).getUserInfo() == null) {
            return;
        }

        Observable.create((ObservableOnSubscribe<ArrayList<QueryDataWrap>>) e -> {
            try {
                ConnectImpl c = new ConnectImpl();

                if (DefaultConfig.ALL) {
                    int tempFlag = Api.flag;
                    for (int i = 0; i < 10; i++) {
                        Api.flag = i;
                        ArrayList<QueryData> query =
                                c.queryGzl(primaryKey(((App) getApplication()).getUserInfo().getUserInfo().getCode()));
                        ArrayList<QueryDataWrap> wraps = new ArrayList<>();
                        for (QueryData q : query) {
                            QueryDataWrap wrap = new QueryDataWrap();
                            wrap.ipFlag = i;
                            wrap.data = q;
                            wraps.add(wrap);
                        }
                        e.onNext(wraps);
                    }
                    Api.flag = tempFlag;
                } else {
                    ArrayList<QueryData> query =
                            c.queryGzl(primaryKey(((App) getApplication()).getUserInfo().getUserInfo().getCode()));
                    ArrayList<QueryDataWrap> wraps = new ArrayList<>();
                    for (QueryData q : query) {
                        QueryDataWrap wrap = new QueryDataWrap();
                        wrap.ipFlag = Api.flag;
                        wrap.data = q;
                        wraps.add(wrap);
                    }
                    e.onNext(wraps);
                }
                e.onComplete();
            } catch (Exception e1) {
                e.onError(e1);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setData, this::onErr, this::onComplete)
                .isDisposed();
    }

    private void onErr(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void onComplete() {
    }

    @SuppressLint("CheckResult")
    private void setData(ArrayList<QueryDataWrap> queryData) {
        for (QueryDataWrap q : queryData) {
            wraps.put(q.data.getID(), q);

            Observable.create((ObservableOnSubscribe<String>) e -> {
                int flag = Api.flag;
                Api.flag = q.ipFlag;
                ArrayList<String> list = new ConnectImpl().queryWenShuBianHao(/*XmlFactory.queryWenShuBianHao(DefaultConfig.userId,*/ q.data.getID());
                if (list.size() == 0) {
                    if (q.data.getTOPIC().contains("行政处罚")) {
                        e.onNext(q.data.getID());
                    } else {
                        wraps.remove(q.data.getID());
                        e.onNext("");
                    }
                } else {
                    //TODO 换成 数量 会更省流量
                    String wenShuNeiRong = new ConnectImpl().queryWenShuNeiRong(list);
                    if (TextUtils.isEmpty(wenShuNeiRong)) {
                        wraps.remove(q.data.getID());
                        e.onNext("");
                    } else {
                        e.onNext("");
                    }
                }
                Api.flag = flag;
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(this::rm, this::onErr, this::onComplete).isDisposed();

        }
        Log.e("setData: ", Thread.currentThread().getName());
    }

    private void rm(String s) {
        Log.e("rm: ", s);
        if (!s.equals("")) {
            QueryDataWrap remove = wraps.remove(s);
            if (remove!=null&&remove.data != null)
                Observable.create((ObservableOnSubscribe<String>) e -> {
                    String xzcf = new ConnectImpl().queryXingZhengChuFaBaoGaoShu(remove.data.getID());
                    if (!TextUtils.isEmpty(xzcf)) {
                        wraps.put(remove.data.getID(), remove);
                        e.onNext(xzcf);
                    } else {
                        wraps.remove(remove.data.getID());
                    }

                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(this::rm2, this::onErr, this::onComplete).isDisposed();
        }
        reSetData();
    }

    private void rm2(String s) {
        Log.e("rm2:>> ", s.substring(0, 20));
        reSetData();
    }


    public void reSetData() {
        ArrayList<QueryDataWrap> queryData = new ArrayList<>();
        Set<String> keys = wraps.keySet();

        for (String key : keys) {
            queryData.add(wraps.get(key));
        }
        data.clear();
        data.addAll(queryData);
        setData();
    }

    private void setData() {
        pendingApprovalData.clear();
        approvalData.clear();
        if (data != null) {
            for (QueryDataWrap dataRecord : data) {
                if (dataRecord.data.getTOPIC().contains("移动端审批")) {
                    approvalData.add(dataRecord);
                } else {
                    switch (dataRecord.data.getMSGSTATE()) {
                        case "0":
                            pendingApprovalData.add(dataRecord);
                            break;
                        case "1":
                            approvalData.add(dataRecord);
                            break;
                    }
                }
            }
        }
        count.setText(String.valueOf(pendingApprovalData.size()));
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (App.app().getToken() != null && !App.app().getToken().equals("") && App.app().getUserInfo() == null) {
            HttpTools.build(Login.class)
                    .login(App.app().getToken())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::callResult, this::onErr, this::onComplete)
                    .isDisposed();
        }

        if (App.app().getUserInfo() != null)
            setUserInfor();
        data.clear();
        queryData();
    }


    public void callResult(LoginBean bean) {
        App.app().setUserInfo(bean);
        setUserInfor();
        MainActivity.startCurrent(this);
    }


    @OnClick(R.id.approval_statistics)
    void approvalStatistics() {
        Intent in = new Intent(this, CaseStatisticsActivity.class);
        startActivity(in);
    }

    @OnClick(R.id.approval_history)
    void approvalHistory() {
        Intent intent = new Intent(this, HistoryApprovalActivity.class);
        intent.putParcelableArrayListExtra("approvalData", approvalData);
        startActivity(intent);
    }

    @OnClick(R.id.query_case)
    void queryCase() {
        Intent intent = new Intent(this, CaseSelectActivity.class);
        startActivity(intent);
    }

    private long lastTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime <= 2000) {
            UaacApi.notifyLogout(this);
            if (dialog.isShowing()) dialog.dismiss();
            super.onBackPressed();
        } else {
            lastTime = System.currentTimeMillis();
            Toast.makeText(this, "请再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }

    public static void startCurrent(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (flag == 1 && App.app().getUserInfo() == null) {
            MainActivity.startCurrent(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (App.app().getUserInfo() != null)
            flag = 2;
        else
            flag = 1;
    }


    private HashMap<String, QueryDataWrap> wraps = new HashMap<>();
}

package com.zhhl.android.policemovecareful.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.Api;
import com.zhhl.android.policemovecareful.ConnectImpl;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.base.BaseActivity;
import com.zhhl.android.policemovecareful.bean.QueryDataWrap;
import com.zhhl.android.policemovecareful.utils.DialogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuan on 2018/8/6.
 * 案件查看
 */

public class PendingCheckActivity extends BaseActivity {
    private static final String TAG = "PendingCheckActivity";
    Button pendingBt;
    ImageView iv;
    Intent intent;
    @BindView(R.id.mContent)
    WebView mWebView;
    @BindView(R.id.desargee)
    Button desargee;
    @BindView(R.id.mHidden)
    TextView mHidden;
    QueryDataWrap queryData;

    private String path = "";

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_pending_check);
        ButterKnife.bind(this);
        dialog = DialogUtils.showProgressDialog(this, "正在努力查找相关文书中");
//        mWebView.setInitialScale(125);
        setTitle("审批", Color.WHITE);//设置标题和颜色
        setBackArrow(R.mipmap.write_fanhui);//设置返回按钮和点击事件
        //初始化
        init();
        //控件监听
//        initListener();
    }


    private void getWsnr() {
        dialog.show();
        Observable.create((ObservableOnSubscribe<String>) e -> {
            try {
                if (queryData != null) {
                    ConnectImpl connect = new ConnectImpl();
                    int flag = Api.flag;
                    Api.flag = queryData.ipFlag;
                    if (!queryData.data.getTOPIC().contains("处罚")) {
                        ArrayList<String> wsbh = connect.queryWenShuBianHao(queryData.data.getID()/*"j56e770h-gf"*/);
                        if (wsbh.size() > 0) {
                            String wsnr = connect.queryWenShuNeiRong(wsbh);
                            e.onNext("file:///" + ConnectImpl.filePath(this, ConnectImpl.deleteText(ConnectImpl.getNoImg(wsnr), "一式两份，一份留存，一份附卷。")));
                        } else {
                            e.onNext("");
                        }
                    } else {
                        String wsnr = connect.queryXingZhengChuFaBaoGaoShu(queryData.data.getID()/*"jlostqun-6wdo"*/);
                        if (wsnr.equals(""))
                            e.onNext("");
                        else {
                            e.onNext("file:///" + ConnectImpl.filePath(this, ConnectImpl.deleteText(ConnectImpl.getNoImg(wsnr), "一式两份，一份留存，一份附卷。")));
                        }
                    }
                    Api.flag = flag;
                }
            } catch (Exception e1) {
                e.onError(e1);
            }

        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::result, this::onErr, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {

    }

    private void onErr(Throwable throwable) {
        dialog.dismiss();
        throwable.printStackTrace();
    }

    private void result(String s) {
        dialog.dismiss();
        if ("".equals(s)) loadBlock();
        else mWebView.loadUrl(s);
    }

    /**
     * 初始化
     */
    private void init() {
        pendingBt = findViewById(R.id.pending_check_activity_pending);
        queryData = getIntent().getParcelableExtra("type");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient());// 设置 WebChromeClient

        WebSettings webSettings = mWebView.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setAppCacheEnabled(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setSupportZoom(false);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setBuiltInZoomControls(false);
            webSettings.setUseWideViewPort(true);
        }
        getWsnr();
    }

    private void loadBlock() {
        mHidden.setVisibility(View.VISIBLE);
    }

    AlertDialog progress;


////                    boolean update = soapConnection.update(XmlFactory.uploadXml(DefaultConfig.userId, DefaultConfig.tableName, queryData.getTOPIC(), Connect.updateText("(移动端审批：同意)", queryData.getTOPIC())));
////
////                    runOnUiThread(() -> {
////                        progress.dismiss();
////                        AlertDialog dialog = update ? DialogUtils.showSuccess(v, () -> startActivity(new Intent(v.getContext(), MainActivity.class))) : DialogUtils.showFailed(PendingCheckActivity.this, () -> onClick(v));
////
////                    });
////                }).start();
//        });
//
//    }

    @OnClick(R.id.pending_check_activity_pending)
    void onClick() {
        if (progress == null)
            progress = DialogUtils.showProgressDialog(PendingCheckActivity.this);
        else progress.show();
        approvalAgree();
    }


    private void approvalAgree() {

        Observable.create((ObservableOnSubscribe<Boolean>) e -> {
            try {
                ConnectImpl connect = new ConnectImpl();
                boolean update = connect.update(queryData.data.getTOPIC(), ConnectImpl.updateText("(移动端审批：同意)", queryData.data.getTOPIC()));
//                boolean update = connect.update("SEQ22022120180900063859`(移动端审批：同意)2018年09月05日违法行为人梁晓宇的呈请行政处罚报告书（审批）", "SEQ22022120180900063859`2018年09月05日违法行为人梁晓宇的呈请行政处罚报告书（审批）");
                e.onNext(update);
            } catch (Exception e1) {
                e.onError(e1);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onSaved, this::onErr, this::onComplete)
                .isDisposed();

    }

    private void onSaved(Boolean aBoolean) {
        progress.dismiss();
        AlertDialog dialog = aBoolean ? DialogUtils.showSuccess(pendingBt, () -> startActivity(new Intent(this, MainActivity.class))) : DialogUtils.showFailed(PendingCheckActivity.this, this::onClick);
    }


    @OnClick(R.id.desargee)
    void ondisAgree() {
        if (progress == null)
            progress = DialogUtils.showProgressDialog(PendingCheckActivity.this);
        else progress.show();
        approvalDisAgree();
    }

    private void approvalDisAgree() {
        Observable.create((ObservableOnSubscribe<Boolean>) e -> {
            try {
                ConnectImpl soapConnection = new ConnectImpl();
                boolean update = soapConnection.update(queryData.data.getTOPIC(), ConnectImpl.updateText("(移动端审批：不同意)", queryData.data.getTOPIC()));
                e.onNext(update);
            } catch (Exception e1) {
                e.onError(e1);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onCancel, this::onErr, this::onComplete)
                .isDisposed();


    }

    private void onCancel(Boolean aBoolean) {
        progress.dismiss();
        AlertDialog dialog = aBoolean ? DialogUtils.showSuccess(desargee, () -> startActivity(new Intent(desargee.getContext(), MainActivity.class))) : DialogUtils.showFailed(PendingCheckActivity.this, this::ondisAgree);
    }
}

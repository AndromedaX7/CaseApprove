package com.zhhl.android.policemovecareful.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by miao on 2018/9/18.
 */
public class ApprovalDetailsActivity extends BaseActivity {

    @BindView(R.id.mHidden)
    TextView mHidden;

    @BindView(R.id.mListView)
    WebView mWebView;
    private QueryDataWrap queryData;
    private ArrayList<String> data = new ArrayList<>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_approval_details);
        setTitle("案件详情", Color.WHITE);
        setBackArrow(R.mipmap.write_fanhui);
        ButterKnife.bind(this);
        dialog = DialogUtils.showProgressDialog(this, "正在努力查找相关文书中");
//        mWebView.setInitialScale(125);
        queryData = getIntent().getParcelableExtra("primaryKey");
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        queryDetails();
    }

    private void queryDetails() {
        dialog.show();
        Observable.create((ObservableOnSubscribe<String>) e -> {
            Log.e("queryDetails: ", Thread.currentThread().getName());

            try {
                ConnectImpl soapConnection = new ConnectImpl();
                int flag = Api.flag;
                Api.flag = queryData.ipFlag;
                if (!queryData.data.getTOPIC().contains("处罚")) {
                    ArrayList<String> wsbh = soapConnection.queryWenShuBianHao(queryData.data.getID());
                    if (wsbh.size() > 0) {
                        String wsnr = soapConnection.queryWenShuNeiRong(wsbh);
                        e.onNext("file:///" + ConnectImpl.filePath(this, ConnectImpl.deleteText(ConnectImpl.getNoImg(wsnr), "一式两份，一份留存，一份附卷。")));
                    } else {
                        e.onNext("");
                    }
                } else {
                    String wsnr = soapConnection.queryXingZhengChuFaBaoGaoShu(queryData.data.getID()/*"jlostqun-6wdo"*/);
                    if (wsnr.equals(""))
                        e.onNext("");
                    else {
                        e.onNext("file:///" + ConnectImpl.filePath(this, ConnectImpl.deleteText(ConnectImpl.getNoImg(wsnr), "一式两份，一份留存，一份附卷。")));
                    }
                }
                Api.flag = flag;
                e.onComplete();
            } catch (Exception e1) {
                e.onError(e1);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::result, this::onErr, this::complete)
                .isDisposed();


//        new Thread(() -> {
//
//            Logger.d("id", queryData.getID());
//            SoapConnection soapConnection = new Connect();
//            if (!queryData.getTOPIC().contains("处罚")) {
//                ArrayList<String> wsbh = soapConnection.queryWenShuBianHao(XmlFactory.queryWenShuBianHao(DefaultConfig.userId, queryData.getID()));
////                for (int i = 0; i < wsbh.size(); i++) {
////                    Logger.d("yyyyy", wsbh.get(i));
////                }
//                if (wsbh.size() > 0) {
//                    String wsnr = soapConnection.queryWenShuNeiRong(XmlFactory.queryWenShuNeiRong(DefaultConfig.userId, wsbh));
//
//                    path = Connect.decodeBase64(this, wsnr);
//                    runOnUiThread(() -> {
////                        Logger.methodStart();
////                        Logger.e("Uri", path);
////                        Logger.methodEnd();
//                        mWebView.loadUrl("file:///" + path);
//                        path = "";
//                    });
//                } else {
//                    runOnUiThread(this::loadBlock);
//                }
//            } else {
//                String wsnr = soapConnection.queryXingZhengChuFaBaoGaoShu(XmlFactory.queryXingZhengChuFaBaoGaoShu(DefaultConfig.userId, queryData.getID()/*"jlostqun-6wdo"*/));
//                if (wsnr.equals(""))
//                    runOnUiThread(this::loadBlock);
//                else {
//                    path = Connect.decodeBase64(this, wsnr);
//                    runOnUiThread(() -> {
////                        Logger.methodStart();
////                        Logger.e("Uri", path);
////                        Logger.methodEnd();
//                        mWebView.loadUrl("file:///" + path);
//                        path = "";
//                    });
//                }
//            }
//        }).start();

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

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


//        if (queryData != null) {
//            mWebView.loadUrl(Connect.urlAddressServer() + queryData.getURL());
//        }
    }

    private void complete() {

    }

    private void onErr(Throwable throwable) {
        dialog.dismiss();
        throwable.printStackTrace();
    }

    private void result(String s) {
        dialog.dismiss();
        Log.e("result: ", Thread.currentThread().getName());
        if (s.equals("")) loadBlock();
        else mWebView.loadUrl(s);
    }


    private void loadBlock() {
        mHidden.setVisibility(View.VISIBLE);
    }


    public static void startCurrent(Context context, QueryDataWrap data) {
        Intent intent = new Intent(context, ApprovalDetailsActivity.class);
        intent.putExtra("primaryKey", data);
        context.startActivity(intent);
    }

}

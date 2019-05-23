package com.zhhl.android.policemovecareful.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhhl.android.policemovecareful.Api;
import com.zhhl.android.policemovecareful.ConnectImpl;
import com.zhhl.android.policemovecareful.DefaultConfig;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.adapter.CaseAdapter;
import com.zhhl.android.policemovecareful.bean.CaseBasicInformation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CaseQueryListActivity extends WaterActivity {
    @BindView(R.id.mCaseData)
    ListView mCaseData;
    @BindView(R.id.noData)
    TextView noData;
    private CaseAdapter mAdapter;

    private String startDate;
    private String endDate;
    private String state;
    private String sname;
    private int count = 0;

    ProgressDialog dialog;
    View inflate;
    ArrayList<CaseBasicInformation> caseBasicInformation = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据加载中");
        Intent intent = getIntent();
        sname = intent.getStringExtra("name");
        startDate = intent.getStringExtra("startDate");
        state = intent.getStringExtra("state");
        endDate = intent.getStringExtra("endDate");

        inflate = View.inflate(this, R.layout.list_footer, null);
        inflate.setVisibility(View.GONE);
        mCaseData.addFooterView(inflate);


        inflate.setOnClickListener((a) -> {
            getData();
        });

        mAdapter = new CaseAdapter(caseBasicInformation);
        mCaseData.setAdapter(mAdapter);
//        Toast .makeText(this,mAdapter.getCount()+"",Toast.LENGTH_SHORT).show();
        showNoDataIfNeed(false);
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_query;
    }

    private void getData() {
        dialog.show();
        Observable.create((ObservableOnSubscribe<ArrayList<CaseBasicInformation>>) e -> {

            try {

                if (DefaultConfig.ALL) {
                    int tempFlag = Api.flag;
                    count++;
                    for (int i = 0; i < 10; i++) {
                        Api.flag = i;
                        e.onNext(new ConnectImpl().queryAnJianJiBenXinXi(startDate, endDate, state, sname, count));
                    }
                    Api.flag = tempFlag;
                } else {
                    e.onNext(
                            new ConnectImpl().queryAnJianJiBenXinXi(startDate, endDate, state, sname, ++count)
                    );
                }
            } catch (Exception e1) {
                e.onError(e1);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::result, this::err, this::complete)
                .isDisposed();


//        new Thread(() -> {
//            caseBasicInformation.clear();
//            SoapConnection soapConnection = new Connect();
//            caseBasicInformation.addAll(Connect.parseBasicInfo(soapConnection.queryAnJianJiBenXinXi(XmlFactory.queryAnJianJiBenXinXi(startDate, endDate, state, sname, ++count))));
//            runOnUiThread(this::addData);
//        }).start();
    }

    private void complete() {
    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
        dialog.dismiss();
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void result(ArrayList<CaseBasicInformation> caseBasicInformation) {

        if (caseBasicInformation.size() > 0) {
            inflate.setVisibility(View.VISIBLE);
        } else {
            inflate.setVisibility(View.GONE);
        }
        mAdapter.addData(caseBasicInformation);
        dialog.dismiss();

//        Toast.makeText(this, "AdapterDataLength:" + mAdapter.getCount() + ":::本次查询：" + caseBasicInformation.size(), Toast.LENGTH_SHORT).show();
        showNoDataIfNeed(mAdapter.getCount() == 0);
    }


//    private void addData() {
//        if (caseBasicInformation.size() > 0) {
//            inflate.setVisibility(View.VISIBLE);
//        } else {
//            inflate.setVisibility(View.GONE);
//        }
//        mAdapter.addData(caseBasicInformation);
//    }

    @OnItemClick(R.id.mCaseData)
    void onItemPress(int i) {
        CaseBasicInformation caseBasicInformation = mAdapter.getData().get(i);
        caseBasicInformation.setUsers(mAdapter.getUsers(i));
        Main3Activity.startCurrent(this, caseBasicInformation);
    }


    public static void startCurrent(Context context, String name, String startDate, String endDate, String state) {
        Intent intent = new Intent(context, CaseQueryListActivity.class);
        intent.putExtra("startDate", startDate);
        intent.putExtra("endDate", endDate);
        intent.putExtra("name", name);
        intent.putExtra("state", state);
        context.startActivity(intent);
    }


    private void showNoDataIfNeed(boolean b) {
        if (mCaseData != null && noData != null) {
            mCaseData.setVisibility(b ? View.GONE : View.VISIBLE);
            noData.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }
}

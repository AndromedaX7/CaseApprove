package com.zhhl.android.policemovecareful.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.ConnectImpl;
import com.zhhl.android.policemovecareful.DateUtil;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.bean.ApprovalInfo;
import com.zhhl.android.policemovecareful.bean.CaseBasicInformation;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main3Activity extends WaterActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.danwei)
    TextView danwei;
    @BindView(R.id.who)
    TextView who;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.details)
    TextView details;
    @BindView(R.id.rule)
    TextView rule;
    @BindView(R.id.result)
    TextView result;
    @BindView(R.id.resultI)
    TextView resultI;
    @BindView(R.id.resultTime)
    TextView resultTime;

    private ArrayList<ApprovalInfo> approvalInfos = new ArrayList<>();
    private CaseBasicInformation data;
    private String admissibleUnit = "";
    private String res = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        data = intent.getParcelableExtra("data");
        approvalInfos.clear();
        name.setText(data.getName());

        date.setText(DateUtil.format("yyyy-MM-dd", DateUtil.parseDate("yyyyMMdd", data.getAdmissibleTime())));
        who.setText(data.getUsers());

        new Thread(() -> {

            ConnectImpl soapConnection = new ConnectImpl();
            ArrayList<String> arrayList = soapConnection.queryDirectory("单位编码", data.getAdmissibleUnit());
            if (arrayList.size() > 0)
                admissibleUnit = arrayList.get(0);

            runOnUiThread(() -> {
                danwei.setText(admissibleUnit);
            });
            if (!data.getState().equals("未受理")) {

                approvalInfos.addAll(soapConnection.queryApprovalInfos(data.getAjbh()));

                if (approvalInfos.size() > 0) {
                    res = approvalInfos.get(0).getSPJG().equals("01") ? "同意" : "不同意";
                }


                runOnUiThread(this::queryResult);
            } else {
                runOnUiThread(() -> {
                    result.setText("");
                    name.setText("未受理");
                });
            }
        }).start();

    }


    private void queryResult() {


//        name.setText(data.getName());

//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < data.getUserInfo().size(); i++) {
//            stringBuilder.append(data.getUserInfo().get(i).getUserName());
//            if (i < data.getUserInfo().size() - 1) {
//                stringBuilder.append("|");
//            }
//        }
//        Toast.makeText(this, "name"+ data.getUsers(),Toast.LENGTH_SHORT).show();

//        Toast.makeText(this,data.getAdmissibleTime(),Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, data.getAdmissibleTime().length() + "", Toast.LENGTH_SHORT).show();

        if (approvalInfos.size() > 0) {
            String flyj = approvalInfos.get(0).getFLYJ();

            rule.setText(flyj == null ? "" : flyj);
            result.setText(res);

            String spyj = approvalInfos.get(0).getSPYJ();
            resultI.setText(spyj == null ? "" : spyj);

            String resTime = approvalInfos.get(0).getSPJG();
            if (resTime.length() < 14)
                resTime = "00000000000000";
            resultTime.setText(String.format(Locale.CHINA, "%s-%s-%s %s:%s:%s",
                    resTime.substring(0, 4),
                    resTime.substring(4, 6),
                    resTime.substring(6, 8),
                    resTime.substring(8, 10),
                    resTime.substring(10, 12),
                    resTime.substring(12, 14)));
        }
    }

    public static void startCurrent(Context context, CaseBasicInformation caseBasicInformation) {
        Intent intent = new Intent(context, Main3Activity.class);
        intent.putExtra("data", caseBasicInformation);
//        Toast.makeText(context, caseBasicInformation.getUsers(), Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }
}

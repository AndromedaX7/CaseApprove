package com.zhhl.android.policemovecareful.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.ConnectImpl;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.utils.DialogUtils;
import com.zhhl.android.policemovecareful.view.Pie;
import com.zhhl.android.policemovecareful.view.TextProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CaseStatisticsActivity extends WaterActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.monthContent)
    LinearLayout monthContent;
    @BindView(R.id.pie)
    Pie pie;

    private ProgressDialog dialog;
    private SparseArray<Integer> map = new SparseArray<>();

    private SparseArray<ViewHolder> holders = new SparseArray<>();

    private int lian = 0;
    private int all = 0;

    int max = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_statistics;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlueTextBar));
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        pie.setMax(1);
        pie.setProgress(0);


        for (int i = 12; i >= 1; i--) {
            View inflate = View.inflate(this, R.layout.item_case_statistics, null);
            ViewHolder viewHolder = new ViewHolder(inflate);
            viewHolder.month.setText(i + "月");
            viewHolder.progress.setMax(1);
            viewHolder.progress.setProgress(0);
            monthContent.addView(inflate);
            holders.put(i, viewHolder);
        }
        dialog = DialogUtils.showProgressDialog(this, "数据加载中");
        getData();
    }

    private void getData() {
        dialog.show();
        try {
            ConnectImpl c = new ConnectImpl();
            String year = DateFormat.format("yyyy", System.currentTimeMillis()).toString();
            new Thread(() -> {
                all = c.queryStatistics("", "", "");
                lian = c.queryStatistics("立案", "", "");


                for (int i = 12; i >= 1; i--) {

                    switch (i) {
                        case 12:
                            map.put(i, c.queryStatistics("立案", year + "" + i + "01", (Integer.parseInt(year) + 1) + "0101"));
                            break;
                        case 11:
                        case 10:
                            map.put(i, c.queryStatistics("立案", year + "" + i + "01", year + "" + (i + 1) + "01"));
                            break;
                        case 9:
                            map.put(i, c.queryStatistics("立案", year + "0" + i + "01", year + "" + (i + 1) + "01"));
                            break;
                        default:
                            map.put(i, c.queryStatistics("立案", year + "0" + i + "01", year + "0" + (i + 1) + "01"));
                            break;
                    }
                }


//            StringBuilder stringBuffer = new StringBuilder();

                for (int i = 1; i <= 12; i++) {
                    max = Math.max(map.get(i), max);
//                stringBuffer.append(i).append("月").append(map.get(i)).append(":::\n");

                }
                max = max / 4 * 5;


                runOnUiThread(this::progressSet);
//            runOnUiThread(() -> {
//                Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
//            });
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
        }
    }


    private void progressSet() {
        dialog.dismiss();
        pie.setMax(all);
        pie.setProgress(lian);

        for (int i = 12; i >= 1; i--) {
            holders.get(i).progress.setMax(max);
            holders.get(i).progress.setProgress(map.get(i));
        }

    }

    static class ViewHolder {
        @BindView(R.id.month)
        TextView month;
        @BindView(R.id.progress)
        TextProgressBar progress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

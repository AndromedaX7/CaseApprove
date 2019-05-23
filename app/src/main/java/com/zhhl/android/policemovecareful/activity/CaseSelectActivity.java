package com.zhhl.android.policemovecareful.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by miao on 2018/9/11.
 */
public class CaseSelectActivity extends WaterActivity {


    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.dateStart)
    TextView dateStart;
    @BindView(R.id.dateEnd)
    TextView dateEnd;
    @BindView(R.id.mQuery)
    TextView mQuery;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.state)
    EditText state;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_select;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setBackgroundColor(0x00000000);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        dateStart.setText(DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()));
        dateEnd.setText(DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()));

    }

    DatePickerDialog dialog;

    @OnClick(R.id.dateStart)
    void setDateStart() {
        String startDate = dateStart.getText().toString();
        String[] sdate = startDate.split("-");
        dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> dateStart.setText(year + "-" + (month + 1 < 10 ? ("0" + (month + 1)) : (month + 1)) + "-" + (dayOfMonth<10?"0"+dayOfMonth:dayOfMonth)), Integer.parseInt(sdate[0]), Integer.parseInt(sdate[1]) - 1, Integer.parseInt(sdate[2]));
        dialog.show();
    }

    @OnClick(R.id.dateEnd)
    void setDateEnd() {

        String endDate = dateEnd.getText().toString();
        String[] sdate = endDate.split("-");
        dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            dateEnd.setText(year + "-" + (month + 1 < 10 ? ("0" + (month + 1)) : (month + 1) )+ "-" + (dayOfMonth<10?"0"+dayOfMonth:dayOfMonth));
        }, Integer.parseInt(sdate[0]), Integer.parseInt(sdate[1]) - 1, Integer.parseInt(sdate[2]));
        dialog.show();
    }


    @OnClick(R.id.mQuery)
    void queryCase() {
        CaseQueryListActivity.startCurrent(this, name.getText().toString(), dateStart.getText().toString().replace("-", ""), dateEnd.getText().toString().replace("-", ""), state.getText().toString());
    }
}

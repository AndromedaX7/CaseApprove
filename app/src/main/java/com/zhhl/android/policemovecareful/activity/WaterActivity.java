package com.zhhl.android.policemovecareful.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xdja.watermarklibrary.WaterMarkUtils;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.base.App;

/**
 * Created by miao on 2019/1/14.
 */
public abstract class WaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (App.app().getUserInfo() != null)
            WaterMarkUtils.addWaterMark(this, App.app().getUserInfo().getUserInfo().getName() + " " + App.app().getUserInfo().getUserInfo().getCode(), 270 + 45, getResources().getColor(R.color.wt), 60);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (App.app().getUserInfo() != null)
            WaterMarkUtils.addWaterMark(this, App.app().getUserInfo().getUserInfo().getName() + " " + App.app().getUserInfo().getUserInfo().getCode(), 270 + 45, getResources().getColor(R.color.wt), 60);
    }

    protected abstract int getLayoutId();
}

package com.zhhl.android.policemovecareful.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xdja.watermarklibrary.WaterMarkUtils;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.base.App;
import com.zhhl.android.policemovecareful.di.component.AppComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity {

    @Inject
    protected P mPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyWaterShadow();
        setContentView(layoutId());
        ButterKnife.bind(this);
        initView();
        componentInject();
        initData();
    }

    private void applyWaterShadow() {
        if (App.app().getUserInfo() != null)
            WaterMarkUtils.addWaterMark(this, App.app().getUserInfo().getUserInfo().getName() + " " + App.app().getUserInfo().getUserInfo().getCode(), 270 + 45, getResources().getColor(R.color.wt), 60);
    }

    protected abstract void initData();

    protected abstract int layoutId();

    protected void initView() {

    }

    private void componentInject() {
        setUpActivityComponent(((OriginApp) getApplication()).getAppComponent());
    }

    protected abstract void setUpActivityComponent(AppComponent appComponent);
}

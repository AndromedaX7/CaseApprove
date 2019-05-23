package com.zhhl.android.policemovecareful.common;

import android.app.Application;

import com.zhhl.android.policemovecareful.di.component.AppComponent;
import com.zhhl.android.policemovecareful.di.component.DaggerAppComponent;
import com.zhhl.android.policemovecareful.di.module.AppModule;
import com.zhhl.android.policemovecareful.di.module.ClientModule;


/**
 * Created by miao on 2019/1/18.
 */
public class OriginApp extends Application {

    AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void setAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .clientModule(new ClientModule())
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setAppComponent();
    }
}

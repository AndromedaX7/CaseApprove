package com.zhhl.android.policemovecareful.di.component;

import com.google.gson.Gson;
import com.zhhl.android.policemovecareful.Login;
import com.zhhl.android.policemovecareful.common.OriginApp;
import com.zhhl.android.policemovecareful.di.module.AppModule;
import com.zhhl.android.policemovecareful.di.module.ClientModule;
import com.zhhl.android.policemovecareful.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {AppModule.class, ClientModule.class, NetworkModule.class})

public interface AppComponent {
    OriginApp application();

    Gson gson();

    OkHttpClient client();

    Login login();


}
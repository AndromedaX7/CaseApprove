package com.zhhl.android.policemovecareful.di.module;

import com.google.gson.Gson;
import com.zhhl.android.policemovecareful.common.OriginApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by miao on 2019/1/17.
 */


@Module
public class AppModule {
    private OriginApp application;

    public AppModule(OriginApp application) {
        this.application = application;
    }

    @Singleton
    @Provides
    OriginApp provideApp() {
        return application;
    }

    @Singleton
    @Provides
    Gson provideGson(){
        return  new Gson();
    }
}

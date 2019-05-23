package com.zhhl.android.policemovecareful.di.module;

import com.zhhl.android.policemovecareful.Api;
import com.zhhl.android.policemovecareful.BuildConfig;
import com.zhhl.android.policemovecareful.Login;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by miao on 2018/6/21.
 */

@Module
public class NetworkModule {

    public static Retrofit retrofitCreatorLogin(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Api.__BASED__.login)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    @Singleton
    @Provides
    Login provideLogin(OkHttpClient client) {
        return retrofitCreatorLogin(client).create(Login.class);
    }
}

package com.zhhl.android.policemovecareful;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by miao on 2018/6/21.
 */
public class HttpTools {
    private static OkHttpClient client;
    private static HashMap<Class<?>, Object> interfaces = new HashMap<>();

    public static OkHttpClient okHttpClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .callTimeout(1, TimeUnit.MINUTES);
            client = builder.build();
        }
        return client;
    }

    public static <T> T build(Class<T> classes) {
        T target = (T) interfaces.get(classes);
        if (target == null) {
            target = new Retrofit.Builder()
                    .baseUrl(DefaultConfig.ip)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient())
                    .build()
                    .create(classes);
            interfaces.put(classes, target);
        }
        return target;
    }

    public static <T> T build(Class<T> classes, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .build()
                .create(classes);
    }

    public static Request request(RequestBody body) {
        return new Request.Builder()
                .url(Api.getIp() + "/tcwebservice/services/ServiceImpl?wsdl=")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("SOAPAction", "a8406bda-053c-4689-a819-e89e5fc7dafc")
                .build();
    }

    public static RequestBody body(String content) {
        return RequestBody.create(MediaType.parse("application/xml"), content);
    }

    public static Request call(String content) {
        return request(body(content));
    }

    public static String execute(String content) {
        try {
            return okHttpClient().newCall(call(content)).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

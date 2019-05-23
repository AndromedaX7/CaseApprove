package com.zhhl.android.policemovecareful;

import com.zhhl.android.policemovecareful.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by miao on 2018/9/27.
 */
public interface Login {
    @FormUrlEncoded
    @POST(DefaultConfig.path)
    Observable<LoginBean> login(@Field("strBill") String token);

}

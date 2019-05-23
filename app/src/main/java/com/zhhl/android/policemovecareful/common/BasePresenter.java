package com.zhhl.android.policemovecareful.common;

import android.widget.Toast;

public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected M mModel;
    protected V mRootView;

    public BasePresenter(M m, V v) {
        this.mModel = m;
        this.mRootView = v;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }

    public void onComplete() {

    }

    public  void onError(Throwable throwable){
        throwable.printStackTrace();
        String err = throwable.getMessage();
        if (err.contains("timeout")){
            Toast.makeText(mRootView.getContext(), "连接超时", Toast.LENGTH_SHORT).show();
        }
    }
}

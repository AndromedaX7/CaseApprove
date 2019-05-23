package com.zhhl.android.policemovecareful.common;


import com.google.gson.Gson;

public abstract class BaseModel implements IModel {
    protected OriginApp application;
    protected Gson gson;

    public BaseModel(OriginApp application, Gson gson) {
        this.application = application;
        this.gson = gson;
    }
}
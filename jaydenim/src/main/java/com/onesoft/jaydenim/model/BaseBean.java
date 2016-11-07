package com.onesoft.jaydenim.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jayden on 2016/7/26.
 */
public class BaseBean<T> {

    @SerializedName("info")
    public List<T> info;

    @SerializedName("statue")
    public String statue;

    /**
     * 提示信息
     */
    @SerializedName("msg")
    public String msg;
}

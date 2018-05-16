package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

/**
 * 单选实体类
 * Created by Jayden on 2016/12/16.
 */

public class SingleSelectBean {
    @SerializedName("name")
    public String name;
    @SerializedName("value")
    public String id;

    public SingleSelectBean(String name, String id) {
        this.name = name;
        this.id = id;
    }
}

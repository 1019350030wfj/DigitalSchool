package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 附件
 * Created by Jayden on 2016/12/29.
 */

public class Attach implements Serializable{

    @SerializedName("id")
    public String id;

    @SerializedName("attach_name")
    public String attach_name;

    @SerializedName("attach_url")
    public String attach_url;

    @SerializedName("new_name")
    public String new_name;

    @SerializedName("size")
    public String size;

    @SerializedName("is_pic")
    public String is_pic;//判断是否是图片类型 ，0 表示不是
}

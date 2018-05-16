package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jayden on 2016/11/5.
 */

public class BoxBean implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("typeid")
    public String typeid;

    @SerializedName("fromusername")
    public String name;

    @SerializedName("fromusertype")
    public String fromusertype;

    @SerializedName("fromuser")
    public String fromuser;

    @SerializedName("photo")
    public String imgUrl;

    @SerializedName("typename")
    public String typename;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @SerializedName("addtime")
    public String time;

    @SerializedName("teacherids")
    public String teacherids;

    @SerializedName("attach_ids")
    public String attach_ids;//有无附件

    @SerializedName("is_read")
    public String is_read;//1已读 0未读

    @SerializedName("receipt")
    public String receipt;// 1是 0否

    public boolean isDelete;
}

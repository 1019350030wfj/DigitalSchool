package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jayden on 2016/12/8.
 */

public class BoxDetail implements Serializable{

    @SerializedName("id")
    public String id;

    @SerializedName("typeid")
    public String typeid;//0是通知，1是消息

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

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


    @SerializedName("addtime")
    public String time;

    @SerializedName("attach_ids")
    public String attach_ids;//附件id

    @SerializedName("teacherids")
    public String teacherids;

    @SerializedName("studentids")
    public String studentids;

    @SerializedName("toteacheruser")
    public String toteacheruser;

    @SerializedName("tostudentuser")
    public String tostudentuser;

    @SerializedName("tousername")
    public String tousername;

    @SerializedName("is_read")
    public String is_read;

    @SerializedName("attach")
    public List<Attach> attach;

}

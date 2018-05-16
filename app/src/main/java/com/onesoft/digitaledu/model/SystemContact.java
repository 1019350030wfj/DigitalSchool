package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 系统通讯录
 * Created by Jayden on 2016/11/8.
 */
public class SystemContact implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("number_id")
    public String the_teacher_id;//教师工号

    @SerializedName("homephone")
    public String homephone;

    @SerializedName("mobilephone")
    public String mobilephone;

    @SerializedName("QQ")
    public String QQ;

    @SerializedName("address")
    public String address;

    @SerializedName("photo")
    public String photo;

    @SerializedName("depart_name")
    public String depart_name;

    @SerializedName("user_type")//1.教师  2.学生
    public String user_type;
}

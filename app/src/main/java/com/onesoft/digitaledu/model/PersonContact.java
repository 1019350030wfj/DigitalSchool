package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 个人通讯录
 * Created by Jayden on 2016/11/30.
 */

public class PersonContact implements Serializable{

    @SerializedName("id")
    public String id;

    @SerializedName("group_id")
    public String group_id;

    @SerializedName("add_user")
    public String add_user;

    @SerializedName("photo")
    public String photo;

    @SerializedName("name")
    public String name;

    @SerializedName("tel")
    public String phoneNumber;

    @SerializedName("add_date")
    public String add_date;

    @SerializedName("type")
    public String type;

    @SerializedName("remark")
    public String remark;

    @SerializedName("status")
    public String status;

    @SerializedName("typename")
    public String typename;
}

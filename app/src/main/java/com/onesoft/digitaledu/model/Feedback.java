package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 意见反馈
 * Created by Jayden on 2016/11/18.
 */

public class Feedback {
    public String type;

    @SerializedName("id")
    public String id;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("user_type")
    public String user_type;

    @SerializedName("mapped_id")
    public String mapped_id;

    @SerializedName("real_name")
    public String nickname;

    @SerializedName("user_name")
    public String user_name;

    @SerializedName("parent_id")
    public String parent_id;

    @SerializedName("children_id")
    public String children_id;

    @SerializedName("photo")
    public String photo;

    @SerializedName("content")
    public String content;

    @SerializedName("create_time")
    public String create_time;

    @SerializedName("time")
    public String time;

    @SerializedName("children")
    public List<Feedback> children;

    @SerializedName("sex")
    public String sex;//0 男 1 女

    public String to_nickname;
    public String to_userId;

}

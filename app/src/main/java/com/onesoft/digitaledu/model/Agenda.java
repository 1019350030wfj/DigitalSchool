package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 日程
 * Created by Jayden on 2016/12/26.
 */

public class Agenda implements Serializable{

    @SerializedName("id")
    public String id;
    public String cid;

    @SerializedName("remark")
    public String remark;

    @SerializedName("title")
    public String title;

    @SerializedName("teacher_name")
    public String teacher_name;

    @SerializedName("the_teacher_id")
    public String the_teacher_id;

    @SerializedName("start")
    public String startTime;
    @SerializedName("end")
    public String endTime;

    public String ad;//是否是全天
    public String rem;//提醒
    public String url;//网站连接
    public String notes;//内容
    public String loc;//地址

    public String day;//天
    @SerializedName("type")
    public String type;//日程类别
    public String imgType;//日程类别
    public String schedule_category_id;//对应日程类别
}

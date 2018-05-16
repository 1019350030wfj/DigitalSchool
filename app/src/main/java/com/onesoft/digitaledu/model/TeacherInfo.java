package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 教师信息
 * Created by Jayden on 2016/12/17.
 */

public class TeacherInfo implements Serializable{

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("sex")
    public String sex;

    @SerializedName("the_teacher_id")
    public String the_teacher_id;

    @SerializedName("jobtitle")
    public String jobtitle;

    public boolean isSelect;
}

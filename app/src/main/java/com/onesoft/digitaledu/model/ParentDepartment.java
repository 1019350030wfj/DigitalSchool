package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

/**
 * 上一级部门
 * Created by Jayden on 2016/12/21.
 */

public class ParentDepartment {

    @SerializedName("id")
    public String id;

    @SerializedName("text")
    public String text;

    @SerializedName("leaf")
    public int leaf;

    @SerializedName("level")
    public String level;

    @SerializedName("parent_depart")
    public String parent_depart;


}

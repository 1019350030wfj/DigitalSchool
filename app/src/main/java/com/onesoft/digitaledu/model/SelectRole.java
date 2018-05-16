package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

/**
 * 选择角色
 * Created by Jayden on 2016/11/24.
 */

public class SelectRole {
    @SerializedName("id")
    public String id;
    @SerializedName("role_name")
    public String role_name;
    @SerializedName("role_menu")
    public String role_menu;
    @SerializedName("menu_id")
    public String menu_id;
    @SerializedName("statu")
    public String statu;
    @SerializedName("type")
    public String type;
    @SerializedName("remark")
    public String remark;


    public boolean isSelect;
}

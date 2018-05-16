package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jayden on 2016/12/21.
 */

public class SelectMember {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
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

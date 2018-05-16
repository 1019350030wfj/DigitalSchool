package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Jayden on 2016/12/9.
 */

public class TopBtn implements Serializable{

    @SerializedName("template_id")
    public String template_id;

    @SerializedName("template_name")
    public String template_name;

    @SerializedName("menuID")
    public String menuID;

    @SerializedName("btn_type")
    public String btn_type;

    @SerializedName("template_url")
    public String template_url;

    @SerializedName("user_type")
    public String user_type;

    @SerializedName("view_field")
    public String view_field;

    @SerializedName("parent_id")
    public String parent_id;

    @SerializedName("notice")
    public String notice;

    @SerializedName("children_id")
    public String children_id;

    public List<Template10Bean> mTemplate10Been;//这个用于列表，也就是Adapter

    public Map<String,Template10Bean> mClientUser;  //这个是方便用key去获取显示item的数据
    public Map<String,Template10Bean> mServerUser;//这个是服务器需要的参数

}

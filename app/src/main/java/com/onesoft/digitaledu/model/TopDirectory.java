package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 一级目录
 * Created by Jayden on 2016/10/28.
 */

public class TopDirectory implements Serializable{

    @SerializedName("id")
    public String id;
    @SerializedName("item_image_default")
    public String imgUrl;
    @SerializedName("item_name")
    public String name;

    @SerializedName("is_leaf")
    public String isLeaf;//1是有，0是没有子菜单
}

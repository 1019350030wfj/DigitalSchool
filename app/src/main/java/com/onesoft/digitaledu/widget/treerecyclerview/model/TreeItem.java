package com.onesoft.digitaledu.widget.treerecyclerview.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jayden on 2016/11/8.
 */

public class TreeItem implements Serializable {

    public static final int ITEM_TYPE_PARENT1 = 1;
    public static final int ITEM_TYPE_PARENT2 = 2;
    public static final int ITEM_TYPE_PARENT3 = 3;
    public static final int ITEM_TYPE_CHILD = 0;

    public String id;
    public String uuid = UUID.randomUUID().toString();//标识每个item的唯一id
    public boolean isSelect;//是否被选中

    @SerializedName("group_id")
    public String group_id;

    @SerializedName("address")
    public String address;

    @SerializedName("text")
    public String name;

    @SerializedName("mobilephone")
    public String mobilephone;//移动电话

    @SerializedName("homephone")
    public String homephone;//办公电话

    @SerializedName("numid")
    public String number;//工号

    @SerializedName("tid")
    public String tid;//工号

    @SerializedName("the_teacher_id")
    public String the_teacher_id;//教师工号

    public int total;//总个数，孩子

    @SerializedName("style")
    public int type;//是父亲还是子  0是叶子

    @SerializedName("leaf")
    public boolean leaf;// 是否是叶子

    public boolean isExpand;// 是否展开

    @SerializedName("photo")
    public String imgUrl;

    @SerializedName("QQ")
    public String QQ;

    @SerializedName("children")
    public List<TreeItem> mChildren;

}

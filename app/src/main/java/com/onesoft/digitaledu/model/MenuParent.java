package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jayden on 2016/11/22.
 */

public class MenuParent {

    @SerializedName("id")
    public String id;

    @SerializedName("text")
    public String text;

    @SerializedName("parent_id")
    public String parent_id;

    @SerializedName("children")
    public String children;

    @SerializedName("leaf")
    public boolean leaf;

    public String title;
}

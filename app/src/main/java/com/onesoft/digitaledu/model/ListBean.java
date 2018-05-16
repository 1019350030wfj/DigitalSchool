package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jayden on 2016/11/23.
 */

public class ListBean implements Serializable{
    @SerializedName("id")
    public String id;

    @SerializedName("item_name")
    public String name;

    public ListBean(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jayden on 2016/11/24.
 */

public class OperationBtnItem {
    @SerializedName("name")
    public String name;
    @SerializedName("value")
    public String id;
    public boolean isSelect;
}

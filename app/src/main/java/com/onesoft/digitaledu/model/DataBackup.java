package com.onesoft.digitaledu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jayden on 2016/12/22.
 */

public class DataBackup {
    @SerializedName("table_name")
    public String table_name;
    @SerializedName("table_comment")
    public String table_comment;

    public boolean isSelect;
}

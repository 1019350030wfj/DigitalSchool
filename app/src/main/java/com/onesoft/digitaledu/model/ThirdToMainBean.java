package com.onesoft.digitaledu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jayden on 2016/12/12.
 */
public class ThirdToMainBean implements Serializable{

    public List<KeyValueBean> mShownList;
    public boolean isExpand = false;//是否展开
    public boolean isDelete = false;
    public String item_btn;
    public String btn_remark;//备注
    public String id;//每一条的信息
    public String building_code;//建筑物编号
    public String building_name;//建筑物名称

}

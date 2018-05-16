package com.onesoft.digitaledu.model;

import java.io.Serializable;

/**
 * 模版10，校区管理  添加  动态模版
 * 每一个布局样式对应的 模版实体数据
 * Created by Jayden on 2016/12/15.
 */

public class Template10Bean implements Serializable{

    public String key;//字段名
    public String name;//字段对应的显示文字

    public String showValue;//显示在页面上的数据，是content对应显示给用户看的数据，天镜夜明
    public String content;//字段对应的真实内容，要上传到服务器的数据

    public String showEditValue;//显示在页面上的数据,这个是编辑页要显示的东西
    public Object originValue;//原先的值,这个是编辑页要显示的东西

    public int mLayoutType;//自己定义的布局样式类型，参考Constant类定义

    public String extend="0";//扩展字段  默认0
}

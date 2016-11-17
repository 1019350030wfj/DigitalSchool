package com.onesoft.digitaledu.widget.treerecyclerview.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jayden on 2016/11/8.
 */

public class TreeItem implements Serializable {

    public static final int ITEM_TYPE_PARENT1 = 0;
    public static final int ITEM_TYPE_PARENT2 = 2;
    public static final int ITEM_TYPE_PARENT3 = 3;
    public static final int ITEM_TYPE_CHILD = 1;

    public String id;
    public String uuid;//标识每个item的唯一id
    public boolean isSelect;//是否被选中
    public String name;
    public String number;//工号
    public int total;//总个数，孩子

    public int type;//是父亲还是子
    public boolean isExpand;// 是否展开
    public String imgUrl;

    public List<TreeItem> mChildren;

}

package com.onesoft.digitaledu.widget.treerecyclerview.interfaces;


import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

public interface ItemDataClickListener {

    void onExpandChildren(TreeItem itemData);

    void onHideChildren(TreeItem itemData);

    void onSelectChange(TreeItem itemData);

}

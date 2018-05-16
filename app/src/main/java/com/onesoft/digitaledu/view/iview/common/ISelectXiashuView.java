package com.onesoft.digitaledu.view.iview.common;

import com.onesoft.digitaledu.view.iview.IBaseView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

import java.util.List;

/**
 * 选择下属
 * Created by Jayden on 2016/12/17.
 */

public interface ISelectXiashuView extends IBaseView{
    void onSuccess(List<TreeItem> treeItems);
}

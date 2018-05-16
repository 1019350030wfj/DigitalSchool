package com.onesoft.digitaledu.view.iview.infomanager.contacts;

import com.onesoft.digitaledu.view.iview.IBaseView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

import java.util.List;

/**
 * Created by Jayden on 2016/11/8.
 */

public interface IGroupManagerView extends IBaseView {

    void onSuccess(List<TreeItem> treeItems);

    void onDelSuccess();
}

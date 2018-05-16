package com.onesoft.digitaledu.view.iview.menu;

import com.onesoft.digitaledu.model.OperationBtnItem;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * Created by Jayden on 2016/11/23.
 */

public interface IOperatorBtnEditView extends IBaseView {

    void onSuccess(List<OperationBtnItem> operationBtnItems);

    void onEditSuccess();
}

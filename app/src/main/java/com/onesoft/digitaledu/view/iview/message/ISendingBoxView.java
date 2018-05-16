package com.onesoft.digitaledu.view.iview.message;

import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * Created by Jayden on 2016/11/5.
 */

public interface ISendingBoxView  extends IBaseView {
    void onSuccess(List<BoxBean> boxBeanList);

    void onDelSuccess();
}

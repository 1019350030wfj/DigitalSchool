package com.onesoft.digitaledu.view.iview.menu;

import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * Created by Jayden on 2016/11/23.
 */

public interface ISearchView extends IBaseView {
    void onSuccess(List<ThirdToMainBean> keyValue);
}

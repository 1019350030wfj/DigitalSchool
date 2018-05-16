package com.onesoft.digitaledu.view.iview;

import com.onesoft.digitaledu.model.ListBean;

import java.util.List;

/**
 * 三级菜单
 * Created by Jayden on 2016/11/23.
 */

public interface IThirdView extends IBaseView {
    void onSuccess(List<ListBean> beans);
}

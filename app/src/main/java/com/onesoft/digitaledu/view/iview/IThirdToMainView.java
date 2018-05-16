package com.onesoft.digitaledu.view.iview;

import com.onesoft.digitaledu.model.MenuTitle;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;

import java.util.List;

/**
 * Created by Jayden on 2016/11/22.
 */

public interface IThirdToMainView extends IBaseView {

    void onSuccess(List<TopBtn> topBtnList, List<MenuTitle> menuTitles, List<ThirdToMainBean> keyValues,String notice);
}

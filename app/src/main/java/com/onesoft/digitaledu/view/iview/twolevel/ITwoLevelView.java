package com.onesoft.digitaledu.view.iview.twolevel;

import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * Created by Jayden on 2016/10/29.
 */

public interface ITwoLevelView extends IBaseView {

    void onGetTwoLevel(String id,List<TopDirectory> twoLevelTitles);
}

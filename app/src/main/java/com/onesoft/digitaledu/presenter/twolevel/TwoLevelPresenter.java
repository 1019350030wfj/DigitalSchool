package com.onesoft.digitaledu.presenter.twolevel;

import android.content.Context;

import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.model.TwoLevelTitle;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.twolevel.ITwoLevelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/10/29.
 */

public class TwoLevelPresenter extends BasePresenter<ITwoLevelView> {

    public TwoLevelPresenter(Context context, ITwoLevelView iView) {
        super(context, iView);
    }

    public void getTwoLevelTitle(String id) {
        List<TwoLevelTitle> twoLevelTitles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TwoLevelTitle twoLevelTitle = new TwoLevelTitle();
            twoLevelTitle.id = String.valueOf(i);
            twoLevelTitle.name = "系统数据 "+i;

            twoLevelTitle.mDirectories = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                TopDirectory topDirectory = new TopDirectory();
                topDirectory.id = String.valueOf(i);
                topDirectory.name = "权限管理" + i + j;
//            topDirectory.imgUrl = "http://pic.wenwen.soso.com/p/20110703/20110703195140-577514640.jpg";
                topDirectory.imgUrl = "";
                twoLevelTitle.mDirectories.add(topDirectory);
            }

            twoLevelTitles.add(twoLevelTitle);
        }
        iView.onGetTwoLevel(twoLevelTitles);
    }
}

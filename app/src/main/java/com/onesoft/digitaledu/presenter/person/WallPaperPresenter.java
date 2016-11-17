package com.onesoft.digitaledu.presenter.person;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.person.IWallPaperView;

/**
 * Created by Jayden on 2016/11/17.
 */

public class WallPaperPresenter extends BasePresenter<IWallPaperView> {
    public WallPaperPresenter(Context context, IWallPaperView iView) {
        super(context, iView);
    }
}

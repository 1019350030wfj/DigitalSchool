package com.onesoft.digitaledu.presenter.person;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.IChangeAvatarView;

/**
 * Created by Jayden on 2016/11/14.
 */

public class ChangeAvatarPresenter extends BasePresenter<IChangeAvatarView> {
    public ChangeAvatarPresenter(Context context, IChangeAvatarView iView) {
        super(context, iView);
    }
}

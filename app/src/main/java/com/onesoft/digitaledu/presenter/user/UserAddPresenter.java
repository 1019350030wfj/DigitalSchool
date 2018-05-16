package com.onesoft.digitaledu.presenter.user;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.user.IUserAddView;

/**
 * Created by Jayden on 2016/11/23.
 */

public class UserAddPresenter extends BasePresenter<IUserAddView> {
    public UserAddPresenter(Context context, IUserAddView iView) {
        super(context, iView);
    }
}

package com.onesoft.digitaledu.presenter.person;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.person.IPersonInfoView;

/**
 * Created by Jayden on 2016/11/14.
 */

public class PersonInfoPresenter extends BasePresenter<IPersonInfoView> {
    public PersonInfoPresenter(Context context, IPersonInfoView iView) {
        super(context, iView);
    }
}

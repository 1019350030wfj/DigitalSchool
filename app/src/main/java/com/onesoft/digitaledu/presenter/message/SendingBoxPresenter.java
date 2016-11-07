package com.onesoft.digitaledu.presenter.message;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.message.ISendingBoxView;

/**
 * Created by Jayden on 2016/11/5.
 */

public class SendingBoxPresenter extends BasePresenter <ISendingBoxView>{

    public SendingBoxPresenter(Context context, ISendingBoxView iView) {
        super(context, iView);
    }
}

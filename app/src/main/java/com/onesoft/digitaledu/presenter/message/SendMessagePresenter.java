package com.onesoft.digitaledu.presenter.message;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.message.ISendMessageView;

/**
 * Created by Jayden on 2016/11/7.
 */

public class SendMessagePresenter extends BasePresenter<ISendMessageView> {
    public SendMessagePresenter(Context context, ISendMessageView iView) {
        super(context, iView);
    }
}

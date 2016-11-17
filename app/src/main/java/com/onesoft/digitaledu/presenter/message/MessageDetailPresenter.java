package com.onesoft.digitaledu.presenter.message;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.message.IMessageDetailView;

/**
 * Created by Jayden on 2016/11/7.
 */

public class MessageDetailPresenter extends BasePresenter<IMessageDetailView> {
    public MessageDetailPresenter(Context context, IMessageDetailView iView) {
        super(context, iView);
    }
}

package com.onesoft.digitaledu.presenter.message;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.BoxDetail;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.message.IMessageDetailSendView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 发件箱详情
 * Created by Jayden on 2016/11/7.
 */

public class MessageDetailSendPresenter extends BasePresenter<IMessageDetailSendView> {
    public MessageDetailSendPresenter(Context context, IMessageDetailSendView iView) {
        super(context, iView);
    }

    public void getMessageDetail(String mesId){
        String url = HttpUrl.SENDBOX_DETAIL + mesId;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<BoxDetail>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<BoxDetail> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info.get(0));
                }
            }
        });
    }

}

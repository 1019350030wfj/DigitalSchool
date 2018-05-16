package com.onesoft.digitaledu.presenter.message;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.BoxDetail;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.message.IMessageDetailView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 收件箱详情
 * Created by Jayden on 2016/11/7.
 */

public class MessageDetailPresenter extends BasePresenter<IMessageDetailView> {

    public MessageDetailPresenter(Context context, IMessageDetailView iView) {
        super(context, iView);
    }

    public void getMessageDetail(String mesId) {
        StringBuilder builder = new StringBuilder();
        builder.append(HttpUrl.INBOX_DETAIL).append("id=").append(mesId);
        builder.append("&mapped_id=").append(SPHelper.getMappedId(mContext));
        builder.append("&user_type=").append(SPHelper.getUserType(mContext));
        HttpHandler.getInstance(mContext).getAsync(mContext, builder.toString(), new HttpHandler.ResultCallback<BaseListBean<BoxDetail>>() {
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

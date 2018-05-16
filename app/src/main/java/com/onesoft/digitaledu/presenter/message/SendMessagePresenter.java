package com.onesoft.digitaledu.presenter.message;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.message.ISendMessageView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 发送消息
 * Created by Jayden on 2016/11/7.
 */

public class SendMessagePresenter extends BasePresenter<ISendMessageView> {

    public SendMessagePresenter(Context context, ISendMessageView iView) {
        super(context, iView);
    }

    /**
     * 发送消息
     *
     * @param touserids
     * @param title
     * @param content
     */
    public void sendMessage(String url,String touserids, String title, String content, String typeid) {
        RemindUtils.showLoading(mContext);
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        params.add(new HttpHandler.Param("mapped_id", SPHelper.getMappedId(mContext)));
        params.add(new HttpHandler.Param("user_type", SPHelper.getUserType(mContext)));
        params.add(new HttpHandler.Param("touserids", touserids));
        params.add(new HttpHandler.Param("title", title));
        params.add(new HttpHandler.Param("content", content));
        params.add(new HttpHandler.Param("typeid", typeid));
        HttpHandler.getInstance(mContext).postAsync(mContext, url,
                params, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }
}

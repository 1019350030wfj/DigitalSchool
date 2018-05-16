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
 * 回复
 * Created by Jayden on 2016/11/7.
 */

public class ReplyMessagePresenter extends BasePresenter<ISendMessageView> {

    public ReplyMessagePresenter(Context context, ISendMessageView iView) {
        super(context, iView);
    }

    /**
     * 回复消息
     * @param touserids
     * @param title
     * @param content
     */
    public void sendMessage(String touserids,String fromusertype,String title,String content){
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.REPLY_MESSAGE;
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("mapped_id", SPHelper.getMappedId(mContext)));
        params.add(new HttpHandler.Param("user_type", SPHelper.getUserType(mContext)));
        params.add(new HttpHandler.Param("fromuser", touserids));
        params.add(new HttpHandler.Param("fromusertype", fromusertype));
        params.add(new HttpHandler.Param("title", title));
        params.add(new HttpHandler.Param("content", content));
        params.add(new HttpHandler.Param("typeid", "1"));
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
                            RemindUtils.showDialog(mContext,response.msg);
                        }
                    }
                });
    }
}

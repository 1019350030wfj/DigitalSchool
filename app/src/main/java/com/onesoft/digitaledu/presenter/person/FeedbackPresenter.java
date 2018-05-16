package com.onesoft.digitaledu.presenter.person;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.Feedback;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.person.IFeedbackView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/11/18.
 */

public class FeedbackPresenter extends BasePresenter<IFeedbackView> {
    public FeedbackPresenter(Context context, IFeedbackView iView) {
        super(context, iView);
    }

    public void getDataFeedback(int page) {
        String url = HttpUrl.FEEDBACK_LIST + page;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<Feedback>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<Feedback> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                } else {
                    iView.onSuccess(null);
                }
            }
        });
    }

    public void delete(String cid) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.FEEDBACK_DELETE + SPHelper.getUserId(mContext) + "&cid=" + cid;
        HttpHandler.getInstance(mContext).getAsync(mContext, url,
                new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onDelSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }

    public void addFeedback(String parentId, String content) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.FEEDBACK_ADD;
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("cid", parentId));
        params.add(new HttpHandler.Param("content", content));
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        HttpHandler.getInstance(mContext).postAsync(mContext, url,
                params, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onAddSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }
}

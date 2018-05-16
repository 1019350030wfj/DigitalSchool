package com.onesoft.digitaledu.presenter.infomanager.docsendrec;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.FilePickBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 发送消息 公文
 * Created by Jayden on 2016/12/29.
 */

public class SendDocPresenter extends BasePresenter<IAddView> {

    public SendDocPresenter(Context context, IAddView iView) {
        super(context, iView);
    }

    /**
     * 发送公文
     *
     * @param touserids
     * @param title
     * @param content
     * @param receipt    回执(1是0否)
     */
    public void sendMessage(String touserids, String title, String content,  String receipt, List<FilePickBean> filePickBeanList) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.SEND_DOCUMENT;
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        params.add(new HttpHandler.Param("touserids", touserids));
        params.add(new HttpHandler.Param("title", title));
        params.add(new HttpHandler.Param("content", content));
        params.add(new HttpHandler.Param("receipt", receipt));
        List<HttpHandler.FileParam> fileParams = new ArrayList<HttpHandler.FileParam>();
        if (filePickBeanList != null && filePickBeanList.size() > 0) {
            int size = filePickBeanList.size();
            for (int i = 0; i < size; i++) {
                fileParams.add(new HttpHandler.FileParam("attach" + (i + 1), new File(filePickBeanList.get(i).getPath())));
            }
        }
        HttpHandler.getInstance(mContext).postAsync(mContext, url,
                params, fileParams, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
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

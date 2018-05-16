package com.onesoft.digitaledu.view.activity.common;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * Created by Jayden on 2017/1/4.
 */

public class AddTeacherToGroupHelper {

    public AddTeacherToGroupHelper() {

    }

    public void addGroupUser(final Context context, String groupId, String teachersId, final IAddView iAddView) {
        String url = HttpUrl.GROUP_ADD_USER + groupId + "&user_ids=" + teachersId + "&user_id=" + SPHelper.getUserId(context);
        HttpHandler.getInstance(context).getAsync(context, url, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<Object> response) {
                RemindUtils.hideLoading();
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iAddView.onAddSuccess();
                } else {
                    RemindUtils.showDialog(context, response.msg);
                }
            }
        });
    }

}

package com.onesoft.digitaledu.presenter.common;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.TeacherInfo;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.common.ISelectTeacherView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 教师列表
 * Created by Jayden on 2016/11/23.
 */

public class TeacherPresenter extends BasePresenter<ISelectTeacherView> {

    public TeacherPresenter(Context context, ISelectTeacherView iView) {
        super(context, iView);
    }

    public void getTeacherList(int page) {
        String url = HttpUrl.SELECT_TEACHER + "?page=" + page ;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<TeacherInfo>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<TeacherInfo> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                }
            }
        });
    }

    /**
     * 教师查询
     * @param page
     * @param query
     */
    public void getTeacherListByQuery(int page,String query) {
        String url = HttpUrl.SELECT_TEACHER + "?page=" + page + "&query=" + query;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<TeacherInfo>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<TeacherInfo> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccessSearch(response.info);
                }
            }
        });
    }
}

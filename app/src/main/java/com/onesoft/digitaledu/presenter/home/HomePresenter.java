package com.onesoft.digitaledu.presenter.home;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/11/29.
 */

public class HomePresenter {

    private IHomeView mView;
    public HomePresenter(IHomeView iHomeView) {
        this.mView = iHomeView;
    }

    public void getTopLevel(Context context) {
        String url = HttpUrl.TOP_LEVEL_MENU + SPHelper.getUserRole(context);
        HttpHandler.getInstance(context).getAsync(context, url,  new HttpHandler.ResultCallback<BaseListBean<TopDirectory>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<TopDirectory> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    mView.onSuccess(response.info);
                }
            }
        });
    }
}

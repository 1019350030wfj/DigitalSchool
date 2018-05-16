package com.onesoft.digitaledu.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.view.iview.IThirdView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/11/23.
 */

public class ThirdMenuPresenter extends BasePresenter<IThirdView> {
    public ThirdMenuPresenter(Context context, IThirdView iView) {
        super(context, iView);
    }

    public void getData(String id) {
        String url = HttpUrl.THIRD_LEVEL_MENU + id;
        HttpHandler.getInstance(mContext).getAsync(mContext, url,  new HttpHandler.ResultCallback<BaseListBean<ListBean>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<ListBean> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                }
            }
        });
    }
}

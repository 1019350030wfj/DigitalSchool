package com.onesoft.digitaledu.presenter.twolevel;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.twolevel.ITwoLevelView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/10/29.
 */

public class TwoLevelPresenter extends BasePresenter<ITwoLevelView> {

    public TwoLevelPresenter(Context context, ITwoLevelView iView) {
        super(context, iView);
    }

    public void getTwoLevelTitle(final String id) {
        String url = HttpUrl.TWO_LEVEL_MENU + id;
        HttpHandler.getInstance(mContext).getAsync(mContext, url,  new HttpHandler.ResultCallback<BaseListBean<TopDirectory>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<TopDirectory> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetTwoLevel(id,response.info);
                }
            }
        });
    }
}

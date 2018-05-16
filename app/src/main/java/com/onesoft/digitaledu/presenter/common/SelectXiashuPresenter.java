package com.onesoft.digitaledu.presenter.common;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.common.ISelectXiashuView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 模版16，选择下属
 * Created by Jayden on 2016/12/16.
 */
public class SelectXiashuPresenter extends BasePresenter<ISelectXiashuView> {

    public SelectXiashuPresenter(Context context, ISelectXiashuView iView) {
        super(context, iView);
    }

    public void getData(int type) {
        String url = HttpUrl.SELECT_XIASHU + "?type=" + type;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<TreeItem>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<TreeItem> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                }
            }
        });
    }


}

package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IPersonContactInfoView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 通讯录 个人通讯录 个人信息
 * Created by Jayden on 2016/12/23.
 */

public class PersonContactInfoPresenter extends BasePresenter<IPersonContactInfoView> {

    public PersonContactInfoPresenter(Context context, IPersonContactInfoView iView) {
        super(context, iView);
    }

    public void delete(String userId) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.PERSON_CONTACT_DELETE + userId;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
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


}

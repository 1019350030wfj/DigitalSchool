package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.SystemContact;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.ISystemContactInfoView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 通讯录 系统通讯录 个人信息
 * Created by Jayden on 2016/11/14.
 */

public class SystemContactInfoPresenter extends BasePresenter<ISystemContactInfoView> {

    public SystemContactInfoPresenter(Context context, ISystemContactInfoView iView) {
        super(context, iView);
    }


    public void getPersonInfo(String userId) {
        String url = HttpUrl.SYSTEM_CONTACT_INFO + userId;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<SystemContact>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<SystemContact> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info.get(0));
                }
            }
        });
    }


}

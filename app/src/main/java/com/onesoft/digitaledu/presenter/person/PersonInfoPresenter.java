package com.onesoft.digitaledu.presenter.person;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.PersonInfo;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.person.IPersonInfoView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 个人信息
 * Created by Jayden on 2016/11/14.
 */

public class PersonInfoPresenter extends BasePresenter<IPersonInfoView> {

    public PersonInfoPresenter(Context context, IPersonInfoView iView) {
        super(context, iView);
    }


    public void getPersonInfo() {
        String url = HttpUrl.PERSON_INFO + SPHelper.getUserId(mContext);
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<PersonInfo>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<PersonInfo> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info.get(0));
                }
            }
        });
    }


}

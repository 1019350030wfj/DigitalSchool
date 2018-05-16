package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.PersonContact;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IPersonContactView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 个人通讯录
 * Created by Jayden on 2016/11/30.
 */

public class PersonContactPresenter extends BasePresenter<IPersonContactView> {

    public PersonContactPresenter(Context context, IPersonContactView iView) {
        super(context, iView);
    }

    public void getPersonContacts() {
        String url = HttpUrl.PERSON_CONTACT + SPHelper.getUserId(mContext);
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<PersonContact>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<PersonContact> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                }
            }
        });
    }

    /**
     * 教师查询
     *
     * @param page
     * @param query
     */
    public void getPersonContactByQuery(int page, String query) {
        String url = HttpUrl.PERSON_CONTACT_SEARCH + SPHelper.getUserId(mContext) + "&page=" + page + "&query=" + query;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<PersonContact>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<PersonContact> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccessSearch(response.info);
                }
            }
        });
    }
}

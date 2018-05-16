package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IContactView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/11/30.
 */

public class ContactPresenter extends BasePresenter<IContactView> {
    public ContactPresenter(Context context, IContactView iView) {
        super(context, iView);
    }

    /**
     * 添加群组
     * @param name
     */
    public void addGroup(String name){
        RemindUtils.showLoading(mContext);
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        params.add(new HttpHandler.Param("name", name));
        HttpHandler.getInstance(mContext).postAsync(mContext, HttpUrl.GROUP_ADD,
                params, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onAddSuccess();
                        } else {
                            RemindUtils.showDialog(mContext,response.msg);
                        }
                    }
                });
    }
}

package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 添加通讯录
 * Created by Jayden on 2016/11/7.
 */

public class ContactAddPresenter extends BasePresenter<IAddView> {

    public ContactAddPresenter(Context context, IAddView iView) {
        super(context, iView);
    }

    /**
     * 添加通讯录
     * @param name
     * @param tel
     * @param content
     * @param type
     */
    public void sendMessage(String name,String tel,String content,String type ){
        RemindUtils.showLoading(mContext);
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        params.add(new HttpHandler.Param("name", name));
        params.add(new HttpHandler.Param("tel", tel));
        params.add(new HttpHandler.Param("type", type));
        params.add(new HttpHandler.Param("remark", content));
        HttpHandler.getInstance(mContext).postAsync(mContext, HttpUrl.PERSON_CONTACT_ADD,
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

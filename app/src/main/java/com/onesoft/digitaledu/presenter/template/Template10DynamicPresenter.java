package com.onesoft.digitaledu.presenter.template;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.template.ITemplate10DynamicView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * 模版10，校区管理  添加 动态
 * Created by Jayden on 2016/11/23.
 */

public class Template10DynamicPresenter extends BasePresenter<ITemplate10DynamicView> {

    public Template10DynamicPresenter(Context context, ITemplate10DynamicView iView) {
        super(context, iView);
    }

    public void addTemplate10(TopBtn topBtn) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.THREE_MENU_TO_DETAIL + topBtn.menuID + "&template_id=" + topBtn.template_id;
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        for (Template10Bean template10Bean : topBtn.mTemplate10Been) {
            params.add(new HttpHandler.Param(template10Bean.key, template10Bean.content));
        }
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        HttpHandler.getInstance(mContext).postAsync(mContext, url,
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
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }
}

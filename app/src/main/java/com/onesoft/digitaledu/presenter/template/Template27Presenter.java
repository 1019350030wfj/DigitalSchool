package com.onesoft.digitaledu.presenter.template;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.iview.template.IEditView;
import com.onesoft.netlibrary.model.HttpHandler;
import com.onesoft.netlibrary.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 模版27，用户编辑
 * Created by Jayden on 2016/12/20.
 */
public class Template27Presenter extends BasePresenter<IEditView> {

    public Template27Presenter(Context context, IEditView iView) {
        super(context, iView);
    }

    public void modify(ThirdToMainBean mainBean, TopBtn topBtn, String newpwd) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.THREE_MENU_TO_DETAIL + topBtn.menuID + "&template_id="+topBtn.template_id;
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("cid", mainBean.id));
        params.add(new HttpHandler.Param("newpwd", SecurityUtils.encodeMD5(newpwd).toLowerCase()));
        HttpHandler.getInstance(mContext).postAsync(mContext, url,
                params, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onEditSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }
}

package com.onesoft.digitaledu.presenter.template;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.TopBtn;
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
 * 模版16，添加权限下属
 * Created by Jayden on 2016/12/16.
 */
public class Template16Presenter extends BasePresenter<IAddView> {

    public Template16Presenter(Context context, IAddView iView) {
        super(context, iView);
    }

    public void add(String parent_teacher_id,int type,String child_teacher_id,String remark, TopBtn topBtn) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.THREE_MENU_TO_DETAIL + topBtn.menuID + "&template_id=" + topBtn.template_id;
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        params.add(new HttpHandler.Param("parent_teacher_id", parent_teacher_id));
        params.add(new HttpHandler.Param("type", String.valueOf(type)));
        params.add(new HttpHandler.Param("child_teacher_ids", child_teacher_id));
        params.add(new HttpHandler.Param("remark", remark));
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

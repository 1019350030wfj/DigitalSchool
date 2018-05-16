package com.onesoft.digitaledu.presenter.template;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.app.Constant;
import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.netlibrary.model.HttpHandler;
import com.onesoft.netlibrary.utils.SecurityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * 添加 动态
 * Created by Jayden on 2016/11/23.
 */
public class Template27DynamicPresenter extends BasePresenter<IAddView> {

    public Template27DynamicPresenter(Context context, IAddView iView) {
        super(context, iView);
    }

    public void addTemplate(ThirdToMainBean keyValueBean, TopBtn topBtn) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.THREE_MENU_TO_DETAIL + topBtn.menuID + "&template_id=" + topBtn.template_id;
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        List<HttpHandler.FileParam> fileParams = new ArrayList<HttpHandler.FileParam>();

        for (Map.Entry<String, Template10Bean> entry : topBtn.mServerUser.entrySet()) {
            Template10Bean template10Bean = entry.getValue();
            if ("user_password".equals(entry.getKey())) {
                params.add(new HttpHandler.Param(template10Bean.key, SecurityUtils.encodeMD5(template10Bean.content).toLowerCase()));
            } else {
                params.add(new HttpHandler.Param(template10Bean.key, template10Bean.content));
            }

            if (template10Bean.mLayoutType == Constant.LAYOUT_STYLE_SELECT_FILE){
                File file = new File(template10Bean.content);
                if(file.exists()){
                    fileParams.add(new HttpHandler.FileParam("upload_file", file));
                }
            }
        }
        if (keyValueBean != null) {//添加教室, 添加办公室
            params.add(new HttpHandler.Param("building_id", keyValueBean.id));
        }
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        HttpHandler.getInstance(mContext).postAsync(mContext, url,
                params, fileParams, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
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

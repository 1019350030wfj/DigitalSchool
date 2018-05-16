package com.onesoft.digitaledu.presenter.menu;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseBean;
import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.OperationBtnItem;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.iview.menu.IOperatorBtnEditView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 操作按钮编辑
 * Created by Jayden on 2016/11/23.
 */

public class OperatorBtnEditPresenter extends BasePresenter<IOperatorBtnEditView> {
    public OperatorBtnEditPresenter(Context context, IOperatorBtnEditView iView) {
        super(context, iView);
    }

    private boolean isRequest;

    public void getSelectBtn() {
        if (!isRequest) {
            isRequest = true;
            HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.SELECT_BTN, new HttpHandler.ResultCallback<BaseBean<OperationBtnItem>>() {
                @Override
                public void onError(Request request, Exception e) {
                    isRequest = false;
                }

                @Override
                public void onResponse(BaseBean<OperationBtnItem> response) {
                    isRequest = false;
                    if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                        iView.onSuccess(response.info);
                    }
                }
            });
        }
    }

    public void modify(ThirdToMainBean mainBean, TopBtn topBtn, String item_btn, String remark) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.THREE_MENU_TO_DETAIL + topBtn.menuID + "&template_id="+topBtn.template_id;
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("cid", mainBean.id));
        params.add(new HttpHandler.Param("item_btn", item_btn));
        params.add(new HttpHandler.Param("btn_remark", remark));
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

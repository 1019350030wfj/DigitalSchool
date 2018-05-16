package com.onesoft.digitaledu.presenter.message;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.message.IRecipientView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 收件人树
 * Created by Jayden on 2016/11/8.
 */

public class RecipientPresenter extends BasePresenter<IRecipientView> {
    public RecipientPresenter(Context context, IRecipientView iView) {
        super(context, iView);
    }

    /**
     * 获取所有用户
     */
    public void getListRecipient() {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.CHOOSE_USER_TREE, new HttpHandler.ResultCallback<BaseListBean<TreeItem>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<TreeItem> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                }
            }
        });
    }

    /**
     * 获取老师列表
     */
    public void getListTeacher() {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.CHOOSE_TEACHER_TREE, new HttpHandler.ResultCallback<BaseListBean<TreeItem>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<TreeItem> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                }
            }
        });
    }
}

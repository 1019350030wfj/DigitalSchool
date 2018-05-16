package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IGroupManagerView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 群组管理
 * Created by Jayden on 2016/11/30.
 */

public class GroupManagerPresenter extends BasePresenter<IGroupManagerView> {

    public GroupManagerPresenter(Context context, IGroupManagerView iView) {
        super(context, iView);
    }

    public void getPersonContacts() {
        String url = HttpUrl.GROUPCONTACT + SPHelper.getUserId(mContext);
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<TreeItem>>() {
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

    public void deleteGroup(String id) {//群组删除
        RemindUtils.showLoading(mContext);
        StringBuilder builder = new StringBuilder();
        builder.append(HttpUrl.GROUP_DELETE).append(id);
        builder.append("&user_id=").append(SPHelper.getUserId(mContext));
        HttpHandler.getInstance(mContext).getAsync(mContext, builder.toString(),
                new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onDelSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }

}

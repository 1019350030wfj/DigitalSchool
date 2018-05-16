package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IPersonContactInfoView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 通讯录 群组通讯录 个人信息
 * Created by Jayden on 2017/01/03.
 */

public class GroupContactInfoPresenter extends BasePresenter<IPersonContactInfoView> {

    public GroupContactInfoPresenter(Context context, IPersonContactInfoView iView) {
        super(context, iView);
    }

    public void delete(String groupId, String delUserId) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.GROUP_CONTACT_DELETE + groupId + "&id=" + delUserId + "&user_id=" + SPHelper.getUserId(mContext);
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
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

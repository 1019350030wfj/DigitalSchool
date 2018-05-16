package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.message.IRecipientView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 系统通讯录
 * Created by Jayden on 2016/11/30.
 */

public class SystemContactPresenter extends BasePresenter<IRecipientView> {

    public SystemContactPresenter(Context context, IRecipientView iView) {
        super(context, iView);
    }

    public void getPersonContacts() {
        String url = HttpUrl.SYSTEM_CONTACT + SPHelper.getUserId(mContext);
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
}

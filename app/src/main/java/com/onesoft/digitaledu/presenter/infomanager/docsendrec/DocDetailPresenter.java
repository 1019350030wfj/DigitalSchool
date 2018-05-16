package com.onesoft.digitaledu.presenter.infomanager.docsendrec;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.BoxDetail;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.infomanager.docsendrec.IDocDetailView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 公文 收件箱详情
 * Created by Jayden on 2016/12/29.
 */

public class DocDetailPresenter extends BasePresenter<IDocDetailView> {

    public DocDetailPresenter(Context context, IDocDetailView iView) {
        super(context, iView);
    }

    public void getMessageDetail(String mesId) {
        StringBuilder builder = new StringBuilder();
        builder.append(HttpUrl.INBOX_DETAIL_DOC).append("id=").append(mesId);
        builder.append("&user_id=").append(SPHelper.getUserId(mContext));
        HttpHandler.getInstance(mContext).getAsync(mContext, builder.toString(), new HttpHandler.ResultCallback<BaseListBean<BoxDetail>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<BoxDetail> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info.get(0));
                }
            }
        });
    }
}

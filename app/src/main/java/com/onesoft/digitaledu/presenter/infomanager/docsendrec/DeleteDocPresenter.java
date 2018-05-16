package com.onesoft.digitaledu.presenter.infomanager.docsendrec;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.infomanager.docsendrec.IDelDocView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

/**
 * 删除公文收件箱或者发件箱
 * Created by Jayden on 2016/12/29.
 */

public class DeleteDocPresenter {

    private Context mContext;
    private IDelDocView mDelMesView;

    public DeleteDocPresenter(Context context, IDelDocView delMesView) {
        this.mContext = context;
        this.mDelMesView = delMesView;
    }

    public void delSendBoxMsg(String id) {//发件箱删除
        RemindUtils.showLoading(mContext);
        StringBuilder builder = new StringBuilder();
        builder.append(HttpUrl.SENDBOX_DELELTE_DOC).append(id);
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
                            mDelMesView.onDelSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }

    /**
     * 收件箱删除
     * id：消息id （单个）
     * （批量删除id传字符串，例如：id：2,3,4）
     * user_id:登录用户
     *
     * @param id
     */
    public void delInBoxMsg(String id) {
        RemindUtils.showLoading(mContext);
        StringBuilder builder = new StringBuilder();
        builder.append(HttpUrl.INBOX_DELELTE_DOC).append(id);
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
                            mDelMesView.onDelSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }
}

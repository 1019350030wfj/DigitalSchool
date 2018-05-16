package com.onesoft.digitaledu.presenter.message;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.message.IInBoxView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/11/5.
 */

public class InBoxPresenter extends BasePresenter<IInBoxView> {

    public InBoxPresenter(Context context, IInBoxView iView) {
        super(context, iView);
    }

    public void getInBoxData(int page) {//收件箱列表
        StringBuilder builder = new StringBuilder();
        builder.append(HttpUrl.INBOX_LIST).append("mapped_id=").append(SPHelper.getMappedId(mContext));
        builder.append("&user_type=").append(SPHelper.getUserType(mContext));
        builder.append("&page=").append(page);
        HttpHandler.getInstance(mContext).getAsync(mContext, builder.toString(), new HttpHandler.ResultCallback<BaseListBean<BoxBean>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<BoxBean> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                } else {
                    iView.onSuccess(null);//205
                }
            }
        });

    }

    /**
     * 批量删除
     *
     * @param beanList
     */
    public void deleteBatch(List<BoxBean> beanList) {
        RemindUtils.showLoading(mContext);
        StringBuilder builder = new StringBuilder();
        StringBuilder builderIds = new StringBuilder();
        for (BoxBean thirdToMainBean : beanList) {
            if (thirdToMainBean.isDelete) {
                builderIds.append(thirdToMainBean.id).append(",");
            }
        }
        if (builderIds.length() > 1) {
            builderIds.deleteCharAt(builderIds.length() - 1);
        }

        builder.append(HttpUrl.INBOX_DELELTE).append(builderIds.toString());
        builder.append("&user_id=").append(SPHelper.getUserId(mContext));
        builder.append("&mapped_id=").append(SPHelper.getMappedId(mContext));
        builder.append("&user_type=").append(SPHelper.getUserType(mContext));
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

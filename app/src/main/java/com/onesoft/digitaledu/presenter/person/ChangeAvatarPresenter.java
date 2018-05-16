package com.onesoft.digitaledu.presenter.person;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.IChangeAvatarView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/11/14.
 */

public class ChangeAvatarPresenter extends BasePresenter<IChangeAvatarView> {
    public ChangeAvatarPresenter(Context context, IChangeAvatarView iView) {
        super(context, iView);
    }

    public void changeAvatar(String avaterPath) {
        RemindUtils.showLoading(mContext);
        String url = HttpUrl.CHANGE_AVATAR + SPHelper.getUserId(mContext);
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        List<HttpHandler.FileParam> files = new ArrayList<HttpHandler.FileParam>();
        if (!TextUtils.isEmpty(avaterPath)) {
            files.add(new HttpHandler.FileParam("image", new File(avaterPath)));
        }
        HttpHandler.getInstance(mContext).postAsync(mContext, url,
                params, files, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }
}

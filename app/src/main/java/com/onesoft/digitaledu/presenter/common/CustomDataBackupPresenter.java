package com.onesoft.digitaledu.presenter.common;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.DataBackup;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.common.IDataBackupView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/11/23.
 */

public class CustomDataBackupPresenter extends BasePresenter<IDataBackupView> {
    public CustomDataBackupPresenter(Context context, IDataBackupView iView) {
        super(context, iView);
    }

    public void getData() {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.DATA_BACKUP,  new HttpHandler.ResultCallback<BaseListBean<DataBackup>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<DataBackup> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info);
                }
            }
        });
    }

    public void backupData(String names,String comments){
        RemindUtils.showLoading(mContext);
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("table_name", names));
        params.add(new HttpHandler.Param("table_comment", comments));
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        HttpHandler.getInstance(mContext).postAsync(mContext, HttpUrl.DATA_BACKUP_POST,
                params, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onDataBackUPSuccess();
                        } else {
                            RemindUtils.showDialog(mContext, response.msg);
                        }
                    }
                });
    }
}

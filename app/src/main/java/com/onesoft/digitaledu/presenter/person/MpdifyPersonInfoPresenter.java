package com.onesoft.digitaledu.presenter.person;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.person.IModifyPersonInfoView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

import static com.onesoft.digitaledu.view.activity.person.ModifyPersonInfoActivity.MODIFY_EMAIL;
import static com.onesoft.digitaledu.view.activity.person.ModifyPersonInfoActivity.MODIFY_HOBBY;
import static com.onesoft.digitaledu.view.activity.person.ModifyPersonInfoActivity.MODIFY_MOTTO;
import static com.onesoft.digitaledu.view.activity.person.ModifyPersonInfoActivity.MODIFY_SPECIALTY;

/**
 * Created by Jayden on 2016/11/14.
 */

public class MpdifyPersonInfoPresenter extends BasePresenter<IModifyPersonInfoView> {

    public MpdifyPersonInfoPresenter(Context context, IModifyPersonInfoView iView) {
        super(context, iView);
    }


    public void editInfo(int type, final String data) {
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(mContext, "请输入内容！", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HttpUrl.PERSON_INFO_EDIT).append(SPHelper.getUserId(mContext));
        stringBuilder.append("&update_key=");
        switch (type) {
            case MODIFY_EMAIL: {
                stringBuilder.append("email");
                break;
            }
            case MODIFY_MOTTO: {
                stringBuilder.append("motto");
                break;
            }
            case MODIFY_HOBBY: {
                stringBuilder.append("habby");
                break;
            }
            case MODIFY_SPECIALTY: {
                stringBuilder.append("specialty");
                break;
            }
        }
        stringBuilder.append("&update_value=");
        stringBuilder.append(data);
        HttpHandler.getInstance(mContext).getAsync(mContext, stringBuilder.toString(),
                new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onSuccess(data);
                        }
                    }
                });
    }


}

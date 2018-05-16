package com.onesoft.digitaledu.presenter.infomanager.contacts;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.iview.person.IModifyPersonInfoView;
import com.onesoft.netlibrary.model.HttpHandler;

import okhttp3.Request;

import static com.onesoft.digitaledu.view.activity.infomanager.contacts.ModifyPersonContactInfoActivity.MODIFY_NAME;
import static com.onesoft.digitaledu.view.activity.infomanager.contacts.ModifyPersonContactInfoActivity.MODIFY_REMARK;
import static com.onesoft.digitaledu.view.activity.infomanager.contacts.ModifyPersonContactInfoActivity.MODIFY_TEL;
import static com.onesoft.digitaledu.view.activity.infomanager.contacts.ModifyPersonContactInfoActivity.MODIFY_TEL_TYPE;

/**
 * 修改通讯录个人信息
 * Created by Jayden on 2016/11/14.
 */

public class ModifyContactPersonInfoPresenter extends BasePresenter<IModifyPersonInfoView> {

    public ModifyContactPersonInfoPresenter(Context context, IModifyPersonInfoView iView) {
        super(context, iView);
    }


    public void editInfo(int type, final String data, String cid) {
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(mContext, "请输入内容！", Toast.LENGTH_SHORT).show();
            return;
        }
        RemindUtils.showLoading(mContext);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HttpUrl.EDIT_CONTACT_PERSON_INFO).append(cid);
        stringBuilder.append("&update_key=");
        switch (type) {
            case MODIFY_NAME: {
                stringBuilder.append("name");
                break;
            }
            case MODIFY_TEL: {
                stringBuilder.append("tel");
                break;
            }
            case MODIFY_TEL_TYPE: {
                stringBuilder.append("type");
                break;
            }
            case MODIFY_REMARK: {
                stringBuilder.append("remark");
                break;
            }
        }
        stringBuilder.append("&update_val=");
        stringBuilder.append(data);
        HttpHandler.getInstance(mContext).getAsync(mContext, stringBuilder.toString(),
                new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onSuccess(data);
                        }else {
                            RemindUtils.showDialog(mContext,response.msg);
                        }
                    }
                });
    }


}

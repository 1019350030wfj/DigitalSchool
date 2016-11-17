package com.onesoft.digitaledu.presenter.person;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.person.IModifyPwdView;

/**
 * Created by Jayden on 2016/11/12.
 */

public class ModifyPwdPresenter extends BasePresenter<IModifyPwdView> {
    public ModifyPwdPresenter(Context context, IModifyPwdView iView) {
        super(context, iView);
    }

    public void modify(Context context, String oldPwd, String newPwd, String confirmPwd) {
        if (TextUtils.isEmpty(oldPwd)) {
            iView.emptyOldPwd();
        } else if (TextUtils.isEmpty(newPwd)) {
            iView.emptyNewPwd();
        } else if (TextUtils.isEmpty(confirmPwd)) {
            iView.emptyConfirmPwd();
        } else if (!newPwd.equals(confirmPwd)) {
            iView.noSamePwd();
        } else {
//            String username = context.getSharedPreferences(WelcomeActivity.PREFERENCE_NAME, MODE_PRIVATE)
//                    .getString(LoginActivity.REMENBER_NAME,"");
//            String oldPwdTemp = SecurityUtils.encodeMD5(oldPwd).toLowerCase();
//            String newPwdTemp = SecurityUtils.encodeMD5(newPwd).toLowerCase();
//            List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
//            params.add(new HttpHandler.Param("user_name", username));
//            params.add(new HttpHandler.Param("old_pwd", oldPwdTemp));
//            params.add(new HttpHandler.Param("new_pwd", newPwdTemp));

//            HttpHandler.getInstance(context).postAsync(context, HttpUrl.MODIFY_PWD, params, new HttpHandler.ResultCallback<BaseBean>() {
//                @Override
//                public void onError(Request request, Exception e) {
//
//                }
//
//                @Override
//                public void onResponse(BaseBean response) {
//                    if (TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
//                        iView.modifySuccess();
//                    } else {
//                        iView.oldPwdError();
//                    }
//                }
//            });

        }
    }
}

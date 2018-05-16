package com.onesoft.digitaledu.presenter.login;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.LoginBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.iview.login.ILoginView;
import com.onesoft.netlibrary.model.HttpHandler;
import com.onesoft.netlibrary.utils.SecurityUtils;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/11/29.
 */

public class LoginPresenter {

    private ILoginView mILoginView;

    public LoginPresenter(ILoginView iLoginView) {
        this.mILoginView = iLoginView;
    }

    public void getAvatar(Context context, final String username) {
        if (TextUtils.isEmpty(username)) {
            mILoginView.emptyUserName();
        }else {
            String url = HttpUrl.LOGIN_AVATAR_URL + "user_name="+username;
            HttpHandler.getInstance(context).getAsync(context, url,  new HttpHandler.ResultCallback<LoginBean>() {
                @Override
                public void onError(Request request, Exception e) {
                }

                @Override
                public void onResponse(LoginBean response) {
                    if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                        mILoginView.avatarSuccess(response);
                    }
                }
            });
        }
    }

    public void login(Context context, final String username, final String pwd) {
        RemindUtils.showLoading(context);
        if (TextUtils.isEmpty(username)) {
            mILoginView.emptyUserName();
        } else if (TextUtils.isEmpty(pwd)) {
            mILoginView.emptyPwd();
        } else {
            String url = HttpUrl.LOGIN_URL + "user_name="+username + "&user_password="+ SecurityUtils.encodeMD5(pwd).toLowerCase();
            HttpHandler.getInstance(context).getAsync(context, url,  new HttpHandler.ResultCallback<LoginBean>() {
                @Override
                public void onError(Request request, Exception e) {
                    RemindUtils.hideLoading();
                    mILoginView.loginError("密码不正确 || 帐号未注册");
                }

                @Override
                public void onResponse(LoginBean response) {
                    RemindUtils.hideLoading();
                    if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                        mILoginView.loginSuccess(response);
                    } else {
                        mILoginView.loginError("密码不正确 || 帐号未注册");
                    }
                }
            });
        }
    }
}

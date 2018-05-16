package com.onesoft.digitaledu.view.iview.login;

import com.onesoft.digitaledu.model.LoginBean;

/**
 * Created by Jayden on 2016/11/29.
 */

public interface ILoginView {
    void loginSuccess(LoginBean loginBean);
    void avatarSuccess(LoginBean loginBean);

    void loginError(String errorMsg);

    void emptyUserName();

    void emptyPwd();
}

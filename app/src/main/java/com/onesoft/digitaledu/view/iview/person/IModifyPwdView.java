package com.onesoft.digitaledu.view.iview.person;

import com.onesoft.digitaledu.view.iview.IBaseView;

/**
 * Created by Jayden on 2016/11/12.
 */

public interface IModifyPwdView extends IBaseView{
    void emptyNewPwd();
    void emptyOldPwd();
    void emptyConfirmPwd();
    void noSamePwd();
    void oldPwdError();
    void modifySuccess();
}

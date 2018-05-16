package com.onesoft.digitaledu.view.iview.infomanager.contacts;

import com.onesoft.digitaledu.model.SystemContact;
import com.onesoft.digitaledu.view.iview.IBaseView;

/**
 * Created by Jayden on 2016/11/14.
 */

public interface ISystemContactInfoView extends IBaseView {

    void onSuccess(SystemContact personInfo);
}

package com.onesoft.digitaledu.view.iview.person;

import com.onesoft.digitaledu.model.PersonInfo;
import com.onesoft.digitaledu.view.iview.IBaseView;

/**
 * Created by Jayden on 2016/12/1.
 */
public interface IEditPersonView extends IBaseView {

    void onSuccess();

    void onSuccess(PersonInfo personInfo);
}

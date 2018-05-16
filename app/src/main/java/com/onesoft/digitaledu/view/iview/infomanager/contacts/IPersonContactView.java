package com.onesoft.digitaledu.view.iview.infomanager.contacts;

import com.onesoft.digitaledu.model.PersonContact;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * Created by Jayden on 2016/11/30.
 */

public interface IPersonContactView extends IBaseView{

    void onSuccess(List<PersonContact> personContacts);

    void onSuccessSearch(List<PersonContact> info);
}

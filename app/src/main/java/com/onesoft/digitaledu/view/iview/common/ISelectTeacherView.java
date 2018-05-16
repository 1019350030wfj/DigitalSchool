package com.onesoft.digitaledu.view.iview.common;

import com.onesoft.digitaledu.model.TeacherInfo;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * Created by Jayden on 2016/12/17.
 */

public interface ISelectTeacherView extends IBaseView {
    void onSuccess(final List<TeacherInfo> menuDetails);

    void onSuccessSearch(final List<TeacherInfo> menuDetails);
}

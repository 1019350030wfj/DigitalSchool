package com.onesoft.digitaledu.view.iview.infomanager.attendancelog;

import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.view.iview.IBaseView;

/**
 *
 * Created by Jayden on 2017/01/04.
 */

public interface IAttendanceDetailView extends IBaseView{
    void onSuccess(Agenda detail);
}

package com.onesoft.digitaledu.widget.calendar.presenter;

import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * Created by Jayden on 2016/12/26.
 */

public interface IMonthView extends IBaseView {
    void onGetYearMonthDataSuccess(List<Agenda> agendas);

    void onGetYearMonthDataError(String msg);

    void onGetDayDataSuccess(final List<Agenda> agendas);

    void onGetDayDataError(String msg);

    void onDeleteSuccess();
}

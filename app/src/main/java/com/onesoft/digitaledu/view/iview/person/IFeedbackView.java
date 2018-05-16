package com.onesoft.digitaledu.view.iview.person;

import com.onesoft.digitaledu.model.Feedback;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * Created by Jayden on 2016/11/18.
 */

public interface IFeedbackView extends IBaseView {
    void onSuccess(List<Feedback> feedbacks);

    void onDelSuccess();

    void onAddSuccess();
}

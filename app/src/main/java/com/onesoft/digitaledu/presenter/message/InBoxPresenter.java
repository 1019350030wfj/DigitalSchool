package com.onesoft.digitaledu.presenter.message;

import android.content.Context;

import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.message.IInBoxView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/11/5.
 */

public class InBoxPresenter extends BasePresenter<IInBoxView> {

    public InBoxPresenter(Context context, IInBoxView iView) {
        super(context, iView);
    }

    public void getInBoxData() {
        List<BoxBean> boxBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BoxBean boxBean = new BoxBean();
            boxBean.id = i+"i";
            boxBean.name = "徐杨峰";
            boxBean.time = "2016-11-05";
            boxBean.title = "新学期军训计划"+i;
            boxBean.content = "您好！祝贺您被升职为厦门凤凰创壹软件有限公司总经理助手。";
            boxBeanList.add(boxBean);
        }
        iView.onSuccess(boxBeanList);
    }
}

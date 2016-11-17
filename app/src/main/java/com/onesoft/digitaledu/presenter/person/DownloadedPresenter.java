package com.onesoft.digitaledu.presenter.person;

import android.content.Context;

import com.onesoft.digitaledu.model.DownloadBean;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.person.IDownloadedView;

import java.util.ArrayList;
import java.util.List;

/**
 * 已经下载的
 * Created by Jayden on 2016/11/9.
 */

public class DownloadedPresenter extends BasePresenter<IDownloadedView> {
    public DownloadedPresenter(Context context, IDownloadedView iView) {
        super(context, iView);
    }


    public void getDownloadData() {
        List<DownloadBean> boxBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DownloadBean boxBean = new DownloadBean();
            boxBean.id = i + "i";
            boxBean.filename = "当前学期课程表.exl";
            boxBean.filesize = "16.63MB";
            boxBeanList.add(boxBean);
        }
        iView.onSuccess(boxBeanList);
    }
}

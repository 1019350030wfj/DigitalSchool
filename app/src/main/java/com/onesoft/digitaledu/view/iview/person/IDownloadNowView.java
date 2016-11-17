package com.onesoft.digitaledu.view.iview.person;

import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * 正在下载
 * Created by Jayden on 2016/11/9.
 */

public interface IDownloadNowView extends IBaseView{
    void onSuccess(List<String> boxBeanList);
}

package com.onesoft.digitaledu.view.iview.common;

import com.onesoft.digitaledu.model.DataBackup;
import com.onesoft.digitaledu.view.iview.IBaseView;

import java.util.List;

/**
 * 数据备份
 * Created by Jayden on 2016/11/23.
 */

public interface IDataBackupView extends IBaseView {
    void onSuccess(List<DataBackup> beans);

    void onDataBackUPSuccess();
}

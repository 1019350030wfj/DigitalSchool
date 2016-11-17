package com.onesoft.digitaledu.presenter.person;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.person.IDownloadNowView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/11/9.
 */

public class DownloadNowPresenter extends BasePresenter<IDownloadNowView> {
    public DownloadNowPresenter(Context context, IDownloadNowView iView) {
        super(context, iView);
    }

    public void getDownloadData() {
        List<String> boxBeanList = new ArrayList<>();
        boxBeanList.add("http://mp4.28mtv.com/mp41/1862-刘德华-余生一起过[68mtv.com].mp4");
        boxBeanList.add("http://img13.360buyimg.com/n1/g14/M01/1B/1F/rBEhVlM03iwIAAAAAAFJnWsj5UAAAK8_gKFgkMAAUm1950.jpg");
        boxBeanList.add("http://sqdd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
        boxBeanList.add("http://down.sandai.net/thunder7/Thunder_dl_7.9.41.5020.exe");
        boxBeanList.add("http://182.254.149.157/ftp/image/shop/product/@#_% &.apk");
        boxBeanList.add("http://dx500.downyouxi.com/minglingyuzhengfu4.rar");
        iView.onSuccess(boxBeanList);
    }
}

package com.onesoft.digitaledu.presenter;

import android.content.Context;

import com.onesoft.digitaledu.view.iview.IBaseView;


/**
 * Created by Jayden on 2016/4/18.
 * Email : 1570713698@qq.com
 */
public abstract class BasePresenter<T extends IBaseView> {

    protected Context mContext;
    protected T iView;

    public BasePresenter(Context context, T iView) {
        this.mContext = context;
        this.iView = iView;
    }
}

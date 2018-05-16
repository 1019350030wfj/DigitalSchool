package com.onesoft.digitaledu.view.template.viewholderadd;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.onesoft.digitaledu.model.TopBtn;

/**
 * Created by Jayden on 2016/12/20.
 */

public abstract class BaseViewHolder {
    protected View view;
    protected Context mContext;
    protected INotifyChange mINotifyChange;

    public abstract View createView(ViewGroup parent);

    public abstract void bindView(TopBtn topBtn,int pos);

    public void setINotifyChange(INotifyChange INotifyChange) {
        mINotifyChange = INotifyChange;
    }

    public interface INotifyChange{
        void onNotifyChange();
    }
}

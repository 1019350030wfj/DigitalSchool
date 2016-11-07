package com.onesoft.digitaledu.view;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Jayden on 2016/10/28.
 */

public abstract class BaseAbsListAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;

    public BaseAbsListAdapter(Context context) {
        this.mContext = context;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

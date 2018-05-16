package com.onesoft.digitaledu.view;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *
 * Created by Jayden on 2016/10/28.
 */

public abstract class BaseAbsListAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;

    protected boolean mISDeleteMode;

    /**
     * 是否是删除模式
     *
     * @param ISDeleteMode
     */
    public void setISDeleteMode(boolean ISDeleteMode) {
        if (mISDeleteMode != ISDeleteMode) {
            mISDeleteMode = ISDeleteMode;
            notifyDataSetChanged();
        }
    }

    public BaseAbsListAdapter(Context context) {
        this.mContext = context;
        mISDeleteMode = false;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param list
     */
    public void addData(final List<T> list) {
        if (mDatas == null) {
            mDatas = list;
        } else {
            mDatas.addAll(list);
        }
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

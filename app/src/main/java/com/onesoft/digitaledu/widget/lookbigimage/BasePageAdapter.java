package com.onesoft.digitaledu.widget.lookbigimage;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * pageAdapter的基本封装
 *
 * @param <T>
 */
public abstract class BasePageAdapter<T> extends PagerAdapter {
    private Activity mContext;
    private LayoutInflater mInflater;
    private List<T> data;

    public BasePageAdapter(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public Activity getContext() {
        return mContext;
    }

    /**
     * 添加数据
     */
    public void resetData(final List<T> list) {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = list;
                notifyDataSetChanged();
            }
        });
    }

    public List<T> getData() {
        return data;
    }

    public T getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T item = getItem(position);
        View convertView = initConvertView(mInflater, item);
        container.addView(convertView);
        return convertView;
    }

    /**
     * 加载布局，添加事件，设置图片展示等
     */
    public abstract View initConvertView(LayoutInflater mInflater, T item);


}

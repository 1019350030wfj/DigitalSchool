package com.onesoft.digitaledu.view.fragment.home;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/10/28.
 */

public class HomePageAdapter extends PagerAdapter {

    private List<View> viewList = new ArrayList<>();

    @Override
    public int getCount() {
        return viewList == null ? 0 : viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    public void setData(List<View> list) {
        this.viewList = list;
        this.notifyDataSetChanged();
    }
}

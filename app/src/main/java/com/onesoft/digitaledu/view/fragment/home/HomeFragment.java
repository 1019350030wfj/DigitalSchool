package com.onesoft.digitaledu.view.fragment.home;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.activity.twolevel.TwoLevelActivity;
import com.onesoft.digitaledu.view.fragment.BaseTitleFragment;
import com.onesoft.digitaledu.widget.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/10/28.
 */

public class HomeFragment extends BaseTitleFragment {

    private ViewPager mViewPager;
    private CircleIndicator mIndicator;
    private HomePageAdapter mHomePageAdapter;
    private List<View> mViews;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
    }

    @Override
    public void initData() {
        getTitleBar().title.setText(getActivity().getResources().getString(R.string.app_name));
        mHomePageAdapter = new HomePageAdapter();
        mViewPager.setAdapter(mHomePageAdapter);

        List<TopDirectory> mDatas = new ArrayList<>();
        for (int i = 0; i < 48; i++) {
            TopDirectory topDirectory = new TopDirectory();
            topDirectory.id = String.valueOf(i);
            topDirectory.name = "标题标题";
//            topDirectory.imgUrl = "http://pic.wenwen.soso.com/p/20110703/20110703195140-577514640.jpg";
            topDirectory.imgUrl = "";
            mDatas.add(topDirectory);
        }

        mViews = new ArrayList<>();
        int count = mDatas.size() % 16 == 0 ? mDatas.size() / 16 : mDatas.size() / 16 + 1;
        for (int i = 0; i < count; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_gridview, null);
            GridView gridView = (GridView) view.findViewById(R.id.gridView);
            final TopDirectoryAdapter topDirectoryAdapter = new TopDirectoryAdapter(getActivity());
            gridView.setAdapter(topDirectoryAdapter);
            if (i < count - 1) {
                topDirectoryAdapter.setDatas(mDatas.subList(16 * i, 16 * (i + 1)));
            } else {
                topDirectoryAdapter.setDatas(mDatas.subList(16 * i, mDatas.size()));
            }

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TwoLevelActivity.startTwoLevelActivity(getActivity(), topDirectoryAdapter.getItem(position));
                }
            });
            mViews.add(view);
        }
        mHomePageAdapter.setData(mViews);
        updateBG();
        mIndicator.setViewPager(mViewPager);
        mIndicator.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.chooseItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(0);
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageStateLayout.onSucceed();
            }
        }, 1000);

        EventBus.getDefault().register(this);
    }

    private void updateBG() {
        if (mViews == null) {
            return;
        }
        for (View view : mViews) {
            //设置壁纸
            ViewUtil.setBackGround(view, SPHelper.getWallPaperPosition(getActivity()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 使用事件总线监听回调
     *
     * @param event
     */
    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(final BaseEvent event) {
        switch (event.type) {
            case BaseEvent.WALLPAPER_UPDATE: {//壁纸
                updateBG();
                break;
            }
        }
    }
}

package com.onesoft.digitaledu.widget.lookbigimage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.onesoft.digitaledu.R;

import java.util.ArrayList;

/**
 * 查看大图
 */
public class LookBigPicActivity extends Activity implements ViewPager.OnPageChangeListener, OnFadeOutListener {

    public static void showBigPic(Context context, ArrayList<String> paths, int defaultPos) {
        Intent intent = new Intent(context, LookBigPicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("paths", paths);
        bundle.putInt("pos", defaultPos);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private HackyViewPager mViewPager;
    private TextView mTvPos;
    private int pos;
    private int all;
    private ArrayList<String> paths;
    private BigPicPageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_big_pic);
        mTvPos = (TextView) findViewById(R.id.tv_pos);
        mViewPager = (HackyViewPager) findViewById(R.id.viewpager);
        mViewPager.setOnPageChangeListener(this);
        initData();
    }

    public void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            finish();
        }
        pos = bundle.getInt("pos");
        paths = bundle.getStringArrayList("paths");
        all = paths == null ? 0 : paths.size();

        mAdapter = new BigPicPageAdapter(this);
        mAdapter.setOnFadeOutListener(this);
        mAdapter.resetData(paths);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(pos);
        onPageSelected(pos);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTvPos.setText((position + 1) + "/" + all);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void fadeOut() {
        finish();
    }

}

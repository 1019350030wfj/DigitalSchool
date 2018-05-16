package com.onesoft.digitaledu.view.fragment;

/**
 * Created by Jayden on 2016/4/18.
 * Email : 1570713698@qq.com
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.pagestate.PageStateLayout;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private LinearLayout rootView;
    protected View mContentView;//内容区域
    protected T mPresenter;

    protected PageStateLayout mPageStateLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = new LinearLayout(getActivity());
        rootView.setOrientation(LinearLayout.VERTICAL);

        mContentView = inflater.inflate(getLayoutResId(), null, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mContentView.setLayoutParams(params);

        mPageStateLayout = new PageStateLayout(getActivity());
        initPageState();
        mPageStateLayout.load(rootView, mContentView);
        mPageStateLayout.onLoading();
        initPresenter();
        return rootView;
    }

    protected void initPageState() {//这边可以自定义空状态页面

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initListener();
        initData(savedInstanceState);
    }

    public void initView(View view) {

    }

    public void initListener() {

    }

    protected void initData(Bundle savedInstanceState) {

    }

    //获取内容布局的资源id
    protected abstract int getLayoutResId();

    protected abstract void initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
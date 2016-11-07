package com.onesoft.digitaledu.view.fragment.message;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.view.activity.MainActivity;
import com.onesoft.digitaledu.view.fragment.BaseTitleFragment;

import java.util.ArrayList;

/**
 * 消息主页
 * Created by Jayden on 2016/10/28.
 */
public class MessageFragment extends BaseTitleFragment {

    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragmentList;

    private View mLlCurrency;
    private View mLlRender;
    private TextView mTxtInBox;
    private TextView mTxtSendingBox;
    private View mIndicatorView1;
    private View mIndicatorView2;

    private boolean mIsDeleteMode = false;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTxtInBox = (TextView) view.findViewById(R.id.txt_inbox);
        mTxtSendingBox = (TextView) view.findViewById(R.id.txt_sending_box);
        mIndicatorView1 = view.findViewById(R.id.indicator1);
        mIndicatorView2 = view.findViewById(R.id.indicator2);
        mLlCurrency = view.findViewById(R.id.ll_currency);
        mLlRender = view.findViewById(R.id.ll_render);
    }

    @Override
    public void initTitleBar(final TitleBar titleBar) {
        titleBar.title.setText(getActivity().getResources().getString(R.string.app_name));
        titleBar.rightPart.setBackgroundResource(R.drawable.bg_btn_more);
        titleBar.rightPart.setVisibility(View.VISIBLE);
        titleBar.rightPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出PopupWindow
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_message_popup, null);
                final PopupWindow popupWindow = new PopupWindow(view,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 控制popupwindow点击屏幕其他地方消失
                popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(
                        R.drawable.icon_mess_popupbox));// 设置背景图片，不能在布局中设置，要通过代码来设置
                popupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
                popupWindow.showAsDropDown(titleBar.rightPart);

                view.findViewById(R.id.txt_del_message).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        showDeleteMode();
                        updateDeleteNum("0");
                        if (mCurSelectPage == 0) {
                            ((InBoxFragment) mFragmentList.get(0)).setBoxAdapterDeleteMode(true);
                        } else {
                            ((BoxFragment) mFragmentList.get(1)).setBoxAdapterDeleteMode(true);
                        }
                    }
                });
            }
        });
        titleBar.leftPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsDeleteMode) {
                    showNoDeleteMode();//退出删除模式
                }
            }
        });
    }

    public void showDeleteMode() {//当是删除模式的时候
        if (!mIsDeleteMode) {
            getTitleBar().leftPart.setVisibility(View.VISIBLE);
            getTitleBar().titleRight.setVisibility(View.VISIBLE);
            getTitleBar().title.setVisibility(View.INVISIBLE);
            getTitleBar().rightPart.setVisibility(View.INVISIBLE);

            ((MainActivity) getActivity()).deleteState();
            mIsDeleteMode = true;
        }
    }

    public void showNoDeleteMode() {//当不是删除模式的时候
        getTitleBar().leftPart.setVisibility(View.INVISIBLE);
        getTitleBar().titleRight.setVisibility(View.INVISIBLE);
        getTitleBar().title.setVisibility(View.VISIBLE);
        getTitleBar().rightPart.setVisibility(View.VISIBLE);

        ((MainActivity) getActivity()).normalState();
        ((InBoxFragment) mFragmentList.get(0)).setBoxAdapterDeleteMode(false);
        ((BoxFragment) mFragmentList.get(1)).setBoxAdapterDeleteMode(false);
        mIsDeleteMode = false;
    }

    public void updateDeleteNum(String content) {
        getTitleBar().titleRight.setText(getActivity().getResources().getString(R.string.already_select_delete, content));
    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(InBoxFragment.newInstance("InBox"));
        mFragmentList.add(BoxFragment.newInstance("SendingBox"));
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurSelectPage = position;
                if (position == 0) {
                    selectPagerOne();
                } else {
                    mTxtInBox.setSelected(false);
                    mTxtSendingBox.setSelected(true);
                    mIndicatorView1.setSelected(false);
                    mIndicatorView2.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        selectPagerOne();
        mLlRender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        mLlCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        mLlCurrency.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageStateLayout.onSucceed();
            }
        }, 2000);
    }

    private int mCurSelectPage;

    private void selectPagerOne() {
        mTxtInBox.setSelected(true);
        mTxtSendingBox.setSelected(false);
        mIndicatorView1.setSelected(true);
        mIndicatorView2.setSelected(false);
    }

    public void setSelectAll() {//全选
        if (mCurSelectPage == 0) {
            ((InBoxFragment) mFragmentList.get(0)).setSelectAll();
        } else {
            ((BoxFragment) mFragmentList.get(1)).setSelectAll();
        }
    }
}

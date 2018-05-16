package com.onesoft.digitaledu.view.activity.infomanager.contacts;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.presenter.infomanager.contacts.ContactPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.fragment.contacts.GroupManagerFragment;
import com.onesoft.digitaledu.view.fragment.contacts.PersonContactFragment;
import com.onesoft.digitaledu.view.fragment.contacts.SystemContactFragment;
import com.onesoft.digitaledu.view.fragment.message.ViewPagerAdapter;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IContactView;
import com.onesoft.digitaledu.widget.dialog.AddGroupDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 通讯录
 * Created by Jayden on 2016/11/30.
 */

public class ContactActivity extends ToolBarActivity<ContactPresenter> implements IContactView {
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragmentList;

    private View mLlCurrency;
    private View mLlRender;
    private View mLlGroup;
    private TextView mTxtInBox;
    private TextView mTxtSendingBox;
    private TextView mTxtGroup;
    private View mIndicatorView1;
    private View mIndicatorView2;
    private View mIndicatorView3;

    @Override
    protected void initPresenter() {
        mPresenter = new ContactPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_contacts, null);
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTxtInBox = (TextView) findViewById(R.id.txt_inbox);
        mTxtGroup = (TextView) findViewById(R.id.txt_group_manager);
        mTxtSendingBox = (TextView) findViewById(R.id.txt_sending_box);
        mIndicatorView1 = findViewById(R.id.indicator1);
        mIndicatorView2 = findViewById(R.id.indicator2);
        mIndicatorView3 = findViewById(R.id.indicator3);
        mLlCurrency = findViewById(R.id.ll_currency);
        mLlRender = findViewById(R.id.ll_render);
        mLlGroup = findViewById(R.id.ll_group_manager);
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.contact));
        mFragmentList = new ArrayList<>();
        mFragmentList.add(SystemContactFragment.newInstance("systemcontact"));
        mFragmentList.add(PersonContactFragment.newInstance("PersonContact"));
        mFragmentList.add(GroupManagerFragment.newInstance("GroupManager"));
        mViewPager.setOffscreenPageLimit(2);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurSelectPage = position;
                if (position == 0) {
                    selectPagerOne();
                } else if (position == 1) {
                    mTxtInBox.setSelected(false);
                    mTxtGroup.setSelected(false);
                    mTxtSendingBox.setSelected(true);
                    mIndicatorView1.setSelected(false);
                    mIndicatorView2.setSelected(true);
                    mIndicatorView3.setSelected(false);
                    if (mNavMenuView != null) {
                        mNavMenuView.setVisibility(View.VISIBLE);
                    }
                } else {
                    mTxtInBox.setSelected(false);
                    mTxtSendingBox.setSelected(false);
                    mTxtGroup.setSelected(true);
                    mIndicatorView1.setSelected(false);
                    mIndicatorView2.setSelected(false);
                    mIndicatorView3.setSelected(true);
                    if (mNavMenuView != null) {
                        mNavMenuView.setVisibility(View.VISIBLE);
                    }
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
        mLlGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        mLlCurrency.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurSelectPage == 0) {
                    mNavMenuView.setVisibility(View.INVISIBLE);
                }
                mPageStateLayout.onSucceed();
            }
        }, 1000);
    }

    private int mCurSelectPage = 0;

    private void selectPagerOne() {
        mTxtInBox.setSelected(true);
        mTxtSendingBox.setSelected(false);
        mTxtGroup.setSelected(false);
        mIndicatorView1.setSelected(true);
        mIndicatorView2.setSelected(false);
        mIndicatorView3.setSelected(false);
        if (mNavMenuView != null) {
            mNavMenuView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
    }

    private View mNavMenuView;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_download);
        mNavMenuView = LayoutInflater.from(this).inflate(R.layout.menu_search, null);
        final View mMenuView = mNavMenuView.findViewById(R.id.riv_menu_main);
        mMenuView.setBackgroundResource(R.drawable.bg_btn_more);
        mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出PopupWindow
                View view = LayoutInflater.from(ContactActivity.this).
                        inflate(R.layout.layout_contact_popup, null);
                final PopupWindow popupWindow = new PopupWindow(view,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 控制popupwindow点击屏幕其他地方消失
                popupWindow.setBackgroundDrawable(ContactActivity.this.getResources().getDrawable(
                        R.drawable.icon_mess_popupbox));// 设置背景图片，不能在布局中设置，要通过代码来设置
                popupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
                popupWindow.showAsDropDown(mMenuView);

                TextView txtAddContact = (TextView) view.findViewById(R.id.txt_user_add);
                TextView txtSearch = (TextView) view.findViewById(R.id.txt_search);
                TextView txtAddGroup = (TextView) view.findViewById(R.id.txt_add_group);
                View view1 = view.findViewById(R.id.view1);
                if (mCurSelectPage == 1) {
                    txtAddGroup.setVisibility(View.GONE);
                    view1.setVisibility(View.VISIBLE);
                    txtSearch.setVisibility(View.VISIBLE);
                    txtAddContact.setVisibility(View.VISIBLE);
                    txtSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//搜索
                            startActivity(new Intent(ContactActivity.this, ContactPersonSearchActivity.class));
                            if (popupWindow.isShowing()) {
                                popupWindow.dismiss();
                            }
                        }
                    });

                    txtAddContact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//添加用户
                            startActivity(new Intent(ContactActivity.this, ContactAddActivity.class));
                            if (popupWindow.isShowing()) {
                                popupWindow.dismiss();
                            }
                        }
                    });
                } else if (mCurSelectPage == 2) {
                    txtAddGroup.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.GONE);
                    txtSearch.setVisibility(View.GONE);
                    txtAddContact.setVisibility(View.GONE);
                    txtAddGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//添加群组
                            if (popupWindow.isShowing()) {
                                popupWindow.dismiss();
                            }
                            AddGroupDialog dialog = new AddGroupDialog(ContactActivity.this);
                            dialog.show();
                            dialog.setListener(new AddGroupDialog.ISelectMsgType() {
                                @Override
                                public void onConfirm(String content) {//添加群组
                                    mPresenter.addGroup(content);
                                }
                            });
                        }
                    });
                }
            }
        });
        if (item != null) {
            item.setActionView(mNavMenuView);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onAddSuccess() {
        //添加群组成功，刷新页面
        EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_GROUP_CONTACT, ""));
    }
}

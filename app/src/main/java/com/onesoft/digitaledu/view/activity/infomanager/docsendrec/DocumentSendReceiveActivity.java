package com.onesoft.digitaledu.view.activity.infomanager.docsendrec;

import android.content.DialogInterface;
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
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.presenter.infomanager.docsendrec.DocumentSendReceivePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.fragment.message.ViewPagerAdapter;
import com.onesoft.digitaledu.view.iview.infomanager.docsendrec.IDocSendReceiveView;
import com.onesoft.digitaledu.view.module.infomanager.ReceiveDocFragment;
import com.onesoft.digitaledu.view.module.infomanager.SendingDocFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 公文收发
 * Created by Jayden on 2016/12/29.
 */
public class DocumentSendReceiveActivity extends ToolBarActivity<DocumentSendReceivePresenter> implements IDocSendReceiveView {
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragmentList;

    private View mLlCurrency;
    private View mLlRender;
    private TextView mTxtInBox;
    private TextView mTxtSendingBox;
    private View mIndicatorView1;
    private View mIndicatorView2;

    private View mLLMessageBottom;
    private TextView mTvAll;

    private boolean mIsDeleteMode = false;
    private boolean isSelectAll = false;

    @Override
    protected void initPresenter() {
        mPresenter = new DocumentSendReceivePresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_document_send_rec, null);
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTxtInBox = (TextView) findViewById(R.id.txt_inbox);
        mTxtSendingBox = (TextView) findViewById(R.id.txt_sending_box);
        mIndicatorView1 = findViewById(R.id.indicator1);
        mIndicatorView2 = findViewById(R.id.indicator2);
        mLlCurrency = findViewById(R.id.ll_currency);
        mLlRender = findViewById(R.id.ll_render);
        mLLMessageBottom = findViewById(R.id.ll_message_bottom);

        mTvAll = (TextView) findViewById(R.id.tv_all);
        ViewUtil.translateToHide(this, mLLMessageBottom);
    }

    @Override
    public void initListener() {
        findViewById(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemindUtils.showDialogWithNag(DocumentSendReceiveActivity.this,
                        getResources().getString(R.string.is_delete), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mCurSelectPage == 0) {//判断当前选中的是哪一页
                                    ((ReceiveDocFragment) mFragmentList.get(0)).delete();
                                } else {
                                    ((SendingDocFragment) mFragmentList.get(1)).delete();
                                }
                            }
                        });
            }
        });

        findViewById(R.id.ll_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelectAll) {
                    mTvAll.setText(getResources().getString(R.string.cancel_all_select));
                } else {
                    mTvAll.setText(getResources().getString(R.string.all_select));
                }
                isSelectAll = !isSelectAll;
                if (mCurSelectPage == 0) {//判断当前选中的是哪一页
                    ((ReceiveDocFragment) mFragmentList.get(0)).setSelectAll(isSelectAll);
                } else {
                    ((SendingDocFragment) mFragmentList.get(1)).setSelectAll(isSelectAll);
                }
            }
        });
    }

    private int mCurSelectPage;

    @Override
    public void initData() {
        setTitle(getString(R.string.document_send_receive));
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ReceiveDocFragment.newInstance("receive"));
        mFragmentList.add(SendingDocFragment.newInstance("send"));
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
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
                updateDeleteNum();
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

        mPageStateLayout.onSucceed();
    }

    private void selectPagerOne() {
        mTxtInBox.setSelected(true);
        mTxtSendingBox.setSelected(false);
        mIndicatorView1.setSelected(true);
        mIndicatorView2.setSelected(false);
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
                View view = LayoutInflater.from(DocumentSendReceiveActivity.this).inflate(R.layout.layout_message_popup, null);
                final PopupWindow popupWindow = new PopupWindow(view,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 控制popupwindow点击屏幕其他地方消失
                popupWindow.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.icon_mess_popupbox));// 设置背景图片，不能在布局中设置，要通过代码来设置
                popupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
                popupWindow.showAsDropDown(mMenuView);

                view.findViewById(R.id.txt_del_message).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//批量删除
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        handleDelete();
                    }
                });

                view.findViewById(R.id.txt_send_message).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//发送消息
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        startActivity(new Intent(DocumentSendReceiveActivity.this, SendDocActivity.class));
                    }
                });

            }
        });
        if (item != null) {
            item.setActionView(mNavMenuView);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void handleDelete() {
        showDeleteMode();
        ((ReceiveDocFragment) mFragmentList.get(0)).setBoxAdapterDeleteMode(true);
        ((SendingDocFragment) mFragmentList.get(1)).setBoxAdapterDeleteMode(true);
    }

    public void showDeleteMode() {//当是删除模式的时候
        if (!mIsDeleteMode) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//变成正常模式
                    showNormalMode();
                }
            });
            setTitle("");
            mNavMenuView.setVisibility(View.INVISIBLE);

            ViewUtil.translateToShow(this, mLLMessageBottom);
            mIsDeleteMode = true;
            updateDeleteNum();
        }
    }

    public void showNormalMode() {//正常模式
        if (mIsDeleteMode) {
            ViewUtil.translateToHide(this, mLLMessageBottom);
            mIsDeleteMode = false;

            initToolbar();
            mNavMenuView.setVisibility(View.VISIBLE);
            setTitle(getString(R.string.document_send_receive));

            ((ReceiveDocFragment) mFragmentList.get(0)).setBoxAdapterDeleteMode(false);
            ((SendingDocFragment) mFragmentList.get(1)).setBoxAdapterDeleteMode(false);
        }
    }

    public void updateDeleteNum() {
        if (mIsDeleteMode) {
            List<BoxBean> list;
            int count = 0;
            if (mCurSelectPage == 0) {
                list = ((ReceiveDocFragment) mFragmentList.get(0)).getDatas();
            } else {
                list = ((SendingDocFragment) mFragmentList.get(1)).getDatas();
            }
            for (BoxBean boxBean : list) {
                if (boxBean.isDelete) {//需要删除的
                    count++;
                }
            }
            getSupportActionBar().setTitle(getResources().getString(R.string.already_select_delete1, "" + count));
        }
    }
}

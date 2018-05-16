package com.onesoft.digitaledu.view.activity.person;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.person.OfflineDownPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.activity.person.download.DownloadingFileFragment;
import com.onesoft.digitaledu.view.fragment.message.ViewPagerAdapter;
import com.onesoft.digitaledu.view.iview.person.IOfflineDownView;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.base.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * 离线下载
 * Created by Jayden on 2016/11/9.
 */

public class OfflineDownloadActivity extends ToolBarActivity<OfflineDownPresenter> implements IOfflineDownView {

    public static void startOfflineActivity(Context context) {
        Intent intent = new Intent(context, OfflineDownloadActivity.class);
        context.startActivity(intent);
    }

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
        mPresenter = new OfflineDownPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_offline_down, null);
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
                RemindUtils.showDialogWithNag(OfflineDownloadActivity.this,
                        getResources().getString(R.string.is_delete), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mCurSelectPage == 0) {//判断当前选中的是哪一页
                                    ((DownloadingFileFragment) mFragmentList.get(0)).delete();
                                } else {
                                    ((DownloadFileFragment) mFragmentList.get(1)).delete();
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
                    ((DownloadingFileFragment) mFragmentList.get(0)).setSelectAll(isSelectAll);
                } else {
                    ((DownloadFileFragment) mFragmentList.get(1)).setSelectAll(isSelectAll);
                }
            }
        });
    }

    private int mCurSelectPage;

    @Override
    public void initData() {
        setTitle(getString(R.string.offline_download));
        showDownloadedSize();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(DownloadingFileFragment.newInstance("InBox"));
        mFragmentList.add(DownloadFileFragment.newInstance("SendingBox"));
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

    public void showDownloadedSize() {
        int count = 0;
        for (DownloadFileInfo downloadFileInfo : FileDownloader.getDownloadFiles()) {
            if (downloadFileInfo.getStatus() == Status.DOWNLOAD_STATUS_COMPLETED) {
                count++;
            }
        }
        mTxtSendingBox.setText(getString(R.string.download_already, "" + count));
    }

    private void selectPagerOne() {
        mTxtInBox.setSelected(true);
        mTxtSendingBox.setSelected(false);
        mIndicatorView1.setSelected(true);
        mIndicatorView2.setSelected(false);
    }

    public void updateShow() {
        ((DownloadFileFragment) mFragmentList.get(1)).updateShow();
        showDownloadedSize();//更新已下载的数量
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
    }

    private View mMenuView;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_download);
        View view = LayoutInflater.from(this).inflate(R.layout.menu_download_file, null);
        mMenuView = view.findViewById(R.id.riv_menu_main);
        mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteMode();
                ((DownloadingFileFragment) mFragmentList.get(0)).setBoxAdapterDeleteMode(true);
                ((DownloadFileFragment) mFragmentList.get(1)).setBoxAdapterDeleteMode(true);
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
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
            updateDeleteNum();
            mMenuView.setVisibility(View.INVISIBLE);

            ViewUtil.translateToShow(this, mLLMessageBottom);
            mIsDeleteMode = true;
        }
    }

    public void showNormalMode() {//正常模式
        if (mIsDeleteMode) {
            ViewUtil.translateToHide(this, mLLMessageBottom);
            mIsDeleteMode = false;

            initToolbar();
            mMenuView.setVisibility(View.VISIBLE);
            setTitle(getString(R.string.offline_download));

            if (mCurSelectPage == 0) {
                ((DownloadingFileFragment) mFragmentList.get(0)).setBoxAdapterDeleteMode(false);
            } else {
                ((DownloadFileFragment) mFragmentList.get(1)).setBoxAdapterDeleteMode(false);
            }
        }
    }

    public void updateDeleteNum() {
        List<DownloadFileInfo> list;
        int count = 0;
        if (mCurSelectPage == 0) {
            list = ((DownloadingFileFragment) mFragmentList.get(0)).getDatas();
        } else {
            list = ((DownloadFileFragment) mFragmentList.get(1)).getDatas();
        }
        for (DownloadFileInfo boxBean : list) {
            if (boxBean.isDelete) {//需要删除的
                count++;
            }
        }
        getSupportActionBar().setTitle(getResources().getString(R.string.already_select_delete1, "" + count));
    }
}

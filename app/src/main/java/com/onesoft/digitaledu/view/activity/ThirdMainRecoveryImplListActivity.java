package com.onesoft.digitaledu.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.MenuTitle;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.ThirdMainToRecoveryPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.publicfoundation.menu.MenuTitleAdapter;
import com.onesoft.digitaledu.view.utils.TemplateHelper;
import com.onesoft.digitaledu.view.utils.TopBtnHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 动态页，到动态页
 * Created by Jayden on 2016/11/26.
 */
public class ThirdMainRecoveryImplListActivity extends ThirdMainToRecoveryListActivity<ThirdMainToRecoveryPresenter> {
    private TemplateHelper mTemplateHelper;

    private boolean isSort = false;//标识是否是排序请求

    private ThirdToMainBean mKeyValueBean;
    private TopBtn mTopBtn;

    public static void startTemplate(Context context, ThirdToMainBean keyValueBean, TopBtn topBtn) {
        Intent intent = new Intent(context, ThirdMainRecoveryImplListActivity.class);
        intent.putExtra("topbtn", topBtn);
        intent.putExtra("keyvalue", keyValueBean);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mKeyValueBean = (ThirdToMainBean) getIntent().getExtras().getSerializable("keyvalue");
            mTopBtn = (TopBtn) getIntent().getExtras().getSerializable("topbtn");
        }
    }


    private TopBtnHelper mTopBtnHelper;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new ThirdMainToRecoveryPresenter(this, this);
    }

    @Override
    public void initData() {
        setTitle(mTopBtn.template_name);
        super.initData();
        mTopBtnHelper = new TopBtnHelper();
        mTemplateHelper = new TemplateHelper();
        mTitleAdapter.setOnClickItemListener(new MenuTitleAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(MenuTitle menuTitle) {
                page = 1;
                isSort = true;
                mPresenter.getDataBySort(mKeyValueBean, mTopBtn, menuTitle, mTopBtn.menuID, 1);
            }
        });
        mListView.refresh();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initListener() {
        super.initListener();
        findViewById(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteBatch(mBatchDeleteBtn, mDetailAdapter.getDatas());
            }
        });
    }

    @Override
    public void onLoadMore() {
        page++;
        mPresenter.getData(mKeyValueBean, mTopBtn, page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getData(mKeyValueBean, mTopBtn, page);
    }

    @Override
    protected void getTitleName() {
        setTitle(mTopBtn.template_name);
    }


    /**
     * 回调是在异步线程，记得切换到主线程
     *
     * @param topBtnList 是标题栏右边要显示按钮的集合，
     *                   0：添加  1：编辑  2：删除   3：导入   4：导出
     *                   5：上传   6：搜索   7：批量删除 8：查看 9、进入
     * @param menuTitles 是搜索栏标题集合
     * @param keyValues  列表item数据集合
     */
    @Override
    public void onSuccess(final List<TopBtn> topBtnList, final List<MenuTitle> menuTitles, final List<ThirdToMainBean> keyValues, final String notice) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.stopRefresh();
                mListView.stopLoadMore();
                if (page == 1) {//这边要通过返回的条数，和限制每一页返回的条数，大小来判断是否还可以加载更多
                    if (!isSort) {//这边我把
                        mTopBtnHelper.handleNav(mMenuView, topBtnList, new ListBean(mKeyValueBean.id, mTopBtn.template_name), new TopBtnHelper.OnPopupItemClickListener() {
                            @Override
                            public void onAdd(TopBtn topBtn) {//添加
                                mTemplateHelper.handleToTemplate(ThirdMainRecoveryImplListActivity.this, mKeyValueBean, topBtn);
                            }

                            @Override
                            public void onBatchDelete(TopBtn topBtn) {//批量删除
                                showDeleteMode(topBtn);
                            }
                        });
                        mTitleAdapter.setTitles(menuTitles);
                        mPageStateLayout.onSucceed();
                        if (!TextUtils.isEmpty(notice)) {//判断是否需要提醒
                            RemindUtils.showDialog(ThirdMainRecoveryImplListActivity.this, notice);
                        }
                    }
                    isSort = false;
                    if (keyValues != null && keyValues.size() > 0) {
                        mDetailAdapter.setDatas(keyValues);
                    }
                } else {
                    if (keyValues != null && keyValues.size() > 0) {
                        mDetailAdapter.addData(keyValues);
                    }
                }
                if (keyValues != null && keyValues.size() > 0) {
                    if (keyValues.size() == HttpUrl.PAGE_SIZE) {
                        mListView.setLoadMoreEnable(true);
                    } else {
                        mListView.setLoadMoreEnable(false);
                    }
                } else {
                    mListView.setLoadMoreEnable(false);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            case BaseEvent.UPDATE_THIRD_MAIN_TO_LIST: {//成功
                onRefresh();//刷新列表
                break;
            }
        }
    }
}

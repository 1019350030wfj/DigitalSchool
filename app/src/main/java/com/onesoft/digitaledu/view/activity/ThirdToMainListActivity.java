package com.onesoft.digitaledu.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.MenuTitle;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.ListPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.publicfoundation.menu.MenuTitleAdapter;
import com.onesoft.digitaledu.view.utils.TemplateHelper;
import com.onesoft.digitaledu.view.utils.TopBtnHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Jayden on 2016/11/26.
 */
public class ThirdToMainListActivity extends AbsThirdtoMainListActivity {


    private TemplateHelper mTemplateHelper;

    private boolean isSort = false;//标识是否是排序请求

    public static void startThirdToMainList(Context context, ListBean listBean) {
        Intent intent = new Intent(context, ThirdToMainListActivity.class);
        intent.putExtra("data", listBean);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mListBean = (ListBean) getIntent().getExtras().getSerializable("data");
        }
    }

    private TopBtnHelper mTopBtnHelper;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new ListPresenter(this, this);
    }

    @Override
    public void initData() {
        setTitle(mListBean.name);
        super.initData();
        mTopBtnHelper = new TopBtnHelper();
        mTemplateHelper = new TemplateHelper();
        mTitleAdapter.setOnClickItemListener(new MenuTitleAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(MenuTitle menuTitle) {
                page = 1;
                isSort = true;
                mPresenter.getDataBySort(menuTitle, mListBean.id, 1);
            }
        });
        mListView.refresh();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void getTitleName() {
        setTitle(mListBean.name);
    }


    /**
     * 回调是在异步线程，记得切换到主线程
     *
     * @param topBtnList 是标题栏右边要显示按钮的集合，
     *                   0：添加  1：编辑  2：删除   3：导入   4：导出
     *                   5：上传   6：搜索   7：批量删除 8：查看
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
                    if (!isSort) {
                        mTopBtnHelper.handleNav(mMenuView, topBtnList, mListBean, new TopBtnHelper.OnPopupItemClickListener() {
                            @Override
                            public void onAdd(TopBtn topBtn) {//添加
                                mTemplateHelper.handleToTemplate(ThirdToMainListActivity.this, null, topBtn);
                            }

                            @Override
                            public void onBatchDelete(TopBtn topBtn) {//批量删除
                                showDeleteMode(topBtn);
                            }
                        });
                        mTitleAdapter.setTitles(menuTitles);
                        mPageStateLayout.onSucceed();
                        if (!TextUtils.isEmpty(notice)) {//判断是否需要提醒
                            RemindUtils.showDialog(ThirdToMainListActivity.this, notice);
                        }
                    }
                    isSort = false;
                    if (keyValues != null && keyValues.size() > 0) {
                        mDetailAdapter.setDatas(keyValues);
                    } else {
                        mPageStateLayout.onEmpty();
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
            case BaseEvent.UPDATE_THIRD_TO_MAIN: {//成功
                onRefresh();//刷新列表
                break;
            }
        }
    }
}

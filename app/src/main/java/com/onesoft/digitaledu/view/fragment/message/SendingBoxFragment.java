package com.onesoft.digitaledu.view.fragment.message;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.message.SendingBoxPresenter;
import com.onesoft.digitaledu.view.activity.message.MessageDetailSendActivity;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.view.iview.message.ISendingBoxView;
import com.onesoft.digitaledu.widget.ptr.PtrListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Jayden on 2016/11/5.
 */

public class SendingBoxFragment extends BaseFragment<SendingBoxPresenter> implements PtrListView.OnRefreshListener, PtrListView.OnLoadMoreListener, ISendingBoxView {

    private PtrListView mListView;
    private BoxAdapter mBoxAdapter;

    public static SendingBoxFragment newInstance(String s) {
        SendingBoxFragment newFragment = new SendingBoxFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        //bundle还可以在每个标签里传送数据
        return newFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_listview;
    }

    @Override
    public void initView(View view) {
        mListView = (PtrListView) view.findViewById(R.id.listview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBoxAdapter = new BoxAdapter(getActivity());
        mBoxAdapter.setOnDeletedListener(new BoxAdapter.OnDeletedListener() {
            @Override
            public void onDelete() {
                ((MessageFragment) getParentFragment()).updateDeleteNum();
            }
        });
        mListView.setAdapter(mBoxAdapter);
        mListView.refresh();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initListener() {
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadMoreListener(this);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((MessageFragment) getParentFragment()).handleDelete();//长按是删除模式
                return true;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageDetailSendActivity.startMessageDetail(getActivity(), mBoxAdapter.getItem(position));
            }
        });
    }


    private boolean mIsDeleteMode;

    public void setBoxAdapterDeleteMode(boolean isDeleteMode) {
        mIsDeleteMode = isDeleteMode;
        mBoxAdapter.setISDeleteMode(isDeleteMode);
    }

    public void setSelectAll(boolean isSelectAll) {//全选
        if (mIsDeleteMode) {
            for (BoxBean boxBean : mBoxAdapter.getDatas()) {
                boxBean.isDelete = isSelectAll;
            }
            mBoxAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter = new SendingBoxPresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(List<BoxBean> boxBeanList) {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        if (page == 1) {//这边要通过返回的条数，和限制每一页返回的条数，大小来判断是否还可以加载更多
            if (boxBeanList != null && boxBeanList.size() > 0) {
                mBoxAdapter.setDatas(boxBeanList);
                mPageStateLayout.onSucceed();
            } else {
                mPageStateLayout.onEmpty();
            }
        } else {
            mBoxAdapter.addData(boxBeanList);
        }
        if (boxBeanList != null && boxBeanList.size() > 0) {
            if (boxBeanList.size() == HttpUrl.PAGE_SIZE) {
                mListView.setLoadMoreEnable(true);
            } else {
                mListView.setLoadMoreEnable(false);
            }
        } else {
            mListView.setLoadMoreEnable(false);
        }
    }

    @Override
    public void onDelSuccess() {
        onRefresh();
        ((MessageFragment) getParentFragment()).updateDeleteNum();
    }

    private int page = 1;

    @Override
    public void onLoadMore() {
        page++;
        mPresenter.getInBoxData(page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getInBoxData(page);
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
            case BaseEvent.SEND_MESSAGE: {//发送消息成功
                onRefresh();//刷新发件箱列表
                break;
            }
        }
    }


    public List<BoxBean> getDatas() {
        if (mBoxAdapter != null) {
            return mBoxAdapter.getDatas();
        }
        return null;
    }

    public void delete() {
        if (mIsDeleteMode) {//是删除模式才做删除操作
            mPresenter.deleteBatch(mBoxAdapter.getDatas());
        }
    }
}

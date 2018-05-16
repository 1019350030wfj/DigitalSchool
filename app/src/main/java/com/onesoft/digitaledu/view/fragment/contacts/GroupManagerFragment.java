package com.onesoft.digitaledu.view.fragment.contacts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.presenter.infomanager.contacts.GroupManagerPresenter;
import com.onesoft.digitaledu.view.activity.common.SelectTeacherMoreActivity;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IGroupManagerView;
import com.onesoft.digitaledu.widget.DividerItemDecoration;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.OnScrollToListener;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 群组管理
 * Created by Jayden on 2016/11/5.
 */
public class GroupManagerFragment extends BaseFragment<GroupManagerPresenter> implements IGroupManagerView {

    private RecyclerView mRecyclerView;
    private ContactGroupAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static GroupManagerFragment newInstance(String s) {
        GroupManagerFragment newFragment = new GroupManagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        //bundle还可以在每个标签里传送数据
        return newFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_system_contacts;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.getItemAnimator().setAddDuration(100);
        mRecyclerView.getItemAnimator().setRemoveDuration(100);
        mRecyclerView.getItemAnimator().setMoveDuration(200);
        mRecyclerView.getItemAnimator().setChangeDuration(100);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapter = new ContactGroupAdapter(getActivity());
        mAdapter.setOperatorListener(new ContactGroupAdapter.ItemOperatorListener() {
            @Override
            public void onSendMessage(TreeItem item) {
            }

            @Override
            public void onAddUsers(TreeItem item) {
                SelectTeacherMoreActivity.startSelectMoreTeacher(getActivity(), item.id);
            }

            @Override
            public void onDelete(TreeItem item) {
                mPresenter.deleteGroup(item.id);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnScrollToListener(new OnScrollToListener() {
            @Override
            public void scrollTo(int position) {
                mRecyclerView.scrollToPosition(position);
            }
        });
        mPresenter.getPersonContacts();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initListener() {
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GroupManagerPresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(List<TreeItem> treeItems) {
        mAdapter.setDatas(treeItems);
        mPageStateLayout.onSucceed();
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
            case BaseEvent.UPDATE_GROUP_CONTACT: {//成功
                mPresenter.getPersonContacts();//刷新列表
                break;
            }
        }
    }

    @Override
    public void onDelSuccess() {
        mPresenter.getPersonContacts();//刷新列表
    }
}

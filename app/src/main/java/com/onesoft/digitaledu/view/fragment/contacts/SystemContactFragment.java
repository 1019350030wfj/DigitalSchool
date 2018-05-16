package com.onesoft.digitaledu.view.fragment.contacts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.infomanager.contacts.SystemContactPresenter;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.view.iview.message.IRecipientView;
import com.onesoft.digitaledu.widget.DividerItemDecoration;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.OnScrollToListener;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

import java.util.List;

/**
 * 系统通讯录
 * Created by Jayden on 2016/11/5.
 */
public class SystemContactFragment extends BaseFragment<SystemContactPresenter> implements IRecipientView {

    private RecyclerView mRecyclerView;
    private SystemConactAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static SystemContactFragment newInstance(String s) {
        SystemContactFragment newFragment = new SystemContactFragment();
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
    protected void initData(Bundle savedInstanceState) {
        mAdapter = new SystemConactAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnScrollToListener(new OnScrollToListener() {
            @Override
            public void scrollTo(int position) {
                mRecyclerView.scrollToPosition(position);
            }
        });
        mPresenter.getPersonContacts();
    }

    @Override
    public void initListener() {
    }

    @Override
    protected void initPresenter() {
        mPresenter = new SystemContactPresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(List<TreeItem> treeItems) {
        mAdapter.addAll(treeItems, 0);
        mPageStateLayout.onSucceed();
    }
}

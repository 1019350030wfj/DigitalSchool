package com.onesoft.digitaledu.widget.treerecyclerview.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.message.RecipientPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.message.IRecipientView;
import com.onesoft.digitaledu.widget.DividerItemDecoration;
import com.onesoft.digitaledu.widget.treerecyclerview.adapter.TreeRecyclerAdapter;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.OnScrollToListener;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

import java.util.List;

/**
 * 收件人
 * Created by Jayden on 2016/11/8.
 */

public class TreeActivity extends ToolBarActivity<RecipientPresenter> implements IRecipientView {

    public static void startRecipientActivity(Context context, int requestCode) {
        Intent intent = new Intent(context, TreeActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    private Button mBtnConfirm;
    private Button mBtnAllSelect;
    private RecyclerView mRecyclerView;
    private TreeRecyclerAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private boolean mIsAllSelect;

    @Override
    protected void initPresenter() {
        mPresenter = new RecipientPresenter(this, this);
    }

    @Override
    public void initView() {
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mBtnAllSelect = (Button) findViewById(R.id.btn_all_select);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.getItemAnimator().setAddDuration(100);
        mRecyclerView.getItemAnimator().setRemoveDuration(100);
        mRecyclerView.getItemAnimator().setMoveDuration(200);
        mRecyclerView.getItemAnimator().setChangeDuration(100);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void initListener() {
        mBtnAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsAllSelect){
                    mBtnAllSelect.setText(getResources().getString(R.string.cancel_all_select));
                } else {
                    mBtnAllSelect.setText(getResources().getString(R.string.all_select));
                }
                mIsAllSelect = !mIsAllSelect;
                mAdapter.setAllSelect(mIsAllSelect);
            }
        });
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initData() {
        mIsAllSelect = false;
        setTitle(getResources().getString(R.string.select_recipient));
        mAdapter = new TreeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnScrollToListener(new OnScrollToListener() {
            @Override
            public void scrollTo(int position) {
                mRecyclerView.scrollToPosition(position);
            }
        });
        mPresenter.getListRecipient();
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_tree_recyclerview, null);
    }

    @Override
    public void onSuccess(final List<TreeItem> treeItems) {
        mAdapter.addAll(treeItems, 0);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageStateLayout.onSucceed();
            }
        }, 2000);
    }
}

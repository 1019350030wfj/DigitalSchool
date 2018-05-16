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
import com.onesoft.digitaledu.presenter.common.SelectXiashuPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.common.ISelectXiashuView;
import com.onesoft.digitaledu.widget.DividerItemDecoration;
import com.onesoft.digitaledu.widget.treerecyclerview.adapter.TreeRecyclerAdapter;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.OnScrollToListener;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

import java.util.List;

/**
 * 下属成员
 * Created by Jayden on 2016/11/8.
 */
public class TreeTemplate16Activity extends ToolBarActivity<SelectXiashuPresenter> implements ISelectXiashuView {

    private int mSelectType;
    private List<TreeItem> mTreeItems;

    public static void startRecipientActivity(Context context, int type, int requestCode) {
        Intent intent = new Intent(context, TreeTemplate16Activity.class);
        intent.putExtra("type", type);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    private Button mBtnConfirm;
    private Button mBtnAllSelect;
    private RecyclerView mRecyclerView;
    private TreeRecyclerAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private StringBuilder builderName;
    private StringBuilder builderChildTeacherIds;

    private boolean mIsAllSelect;

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mSelectType = getIntent().getExtras().getInt("type");
        }
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new SelectXiashuPresenter(this, this);
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
                if (!mIsAllSelect) {
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
                builderName = new StringBuilder();
                builderChildTeacherIds = new StringBuilder();
                getBackDataFromTreeData(mTreeItems);
                if (builderName.length() > 1) {
                    builderName.deleteCharAt(builderName.length() - 1);
                }
                if (builderChildTeacherIds.length() > 1) {
                    builderChildTeacherIds.deleteCharAt(builderChildTeacherIds.length() - 1);
                }
                Intent intent = new Intent();//确定
                intent.putExtra("name", builderName.toString());//找到选中的老师名称集合
                intent.putExtra("id", builderChildTeacherIds.toString());//找到选中的老师id集合
                setResult(RESULT_OK, intent);
                finish();//找到退出
            }
        });
    }

    /**
     * 递归获取选中的成员id和名称
     *
     * @param treeItems
     */
    private void getBackDataFromTreeData(List<TreeItem> treeItems) {
        if (treeItems == null || treeItems.size() == 0) {
            return;
        }
        for (TreeItem item : treeItems) {
            if (item.isSelect && item.leaf) {
                builderName.append(item.name).append(",");
                builderChildTeacherIds.append(item.id).append(",");
            }
            getBackDataFromTreeData(item.mChildren);
        }
    }

    @Override
    public void initData() {
        mIsAllSelect = false;

        setTitle(getResources().getString(R.string.select_subordinate_member));
        mAdapter = new TreeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnScrollToListener(new OnScrollToListener() {
            @Override
            public void scrollTo(int position) {
                mRecyclerView.scrollToPosition(position);
            }
        });
        mPresenter.getData(mSelectType);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_tree_recyclerview, null);
    }

    @Override
    public void onSuccess(final List<TreeItem> treeItems) {
        mTreeItems = treeItems;
        mAdapter.addAll(treeItems, 0);
        mPageStateLayout.onSucceed();
    }
}

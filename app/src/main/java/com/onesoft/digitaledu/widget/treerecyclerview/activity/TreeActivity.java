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
import com.onesoft.digitaledu.model.UserTreeEvent;
import com.onesoft.digitaledu.presenter.message.RecipientPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.message.IRecipientView;
import com.onesoft.digitaledu.widget.DividerItemDecoration;
import com.onesoft.digitaledu.widget.treerecyclerview.adapter.TreeRecyclerAdapter;
import com.onesoft.digitaledu.widget.treerecyclerview.interfaces.OnScrollToListener;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.onesoft.digitaledu.view.activity.infomanager.docsendrec.SendDocActivity.REQUEST_CODE_TEACHER;

/**
 * 收件人
 * Created by Jayden on 2016/11/8.
 */
public class TreeActivity extends ToolBarActivity<RecipientPresenter> implements IRecipientView {

    private int mRequestCode = 0;
    private boolean isFromNotActvity = true;//默认是从Activity跳转到这个页面，其它就是从动态页

    public static void startRecipientActivity(Context context, int requestCode) {//从Activity
        Intent intent = new Intent(context, TreeActivity.class);
        intent.putExtra("requestcode", requestCode);
        intent.putExtra("where", false);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void startUserTreeActivity(Context context, boolean fromNotActivity) {//从动态页
        Intent intent = new Intent(context, TreeActivity.class);
        intent.putExtra("where", fromNotActivity);
        intent.putExtra("requestcode", 0);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            isFromNotActvity = getIntent().getExtras().getBoolean("where");
            mRequestCode = getIntent().getExtras().getInt("requestcode");
        }
    }

    private Button mBtnConfirm;
    private Button mBtnAllSelect;
    private RecyclerView mRecyclerView;
    private TreeRecyclerAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private boolean mIsAllSelect;
    private List<TreeItem> mTreeItems;
    private StringBuilder builderName;
    private StringBuilder builderChildTeacherIds;

    @Override
    protected void initPresenter() {
        getDataFromForward();
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
                if (isFromNotActvity) {//不是Activity, 回传数据
                    EventBus.getDefault().post(new UserTreeEvent(builderName.toString(), builderChildTeacherIds.toString()));
                } else {
                    Intent intent = new Intent();//确定
                    intent.putExtra("name", builderName.toString());//找到选中的老师名称集合
                    intent.putExtra("id", builderChildTeacherIds.toString());//找到选中的老师id集合
                    setResult(RESULT_OK, intent);
                }
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
        setTitle(getResources().getString(R.string.select_recipient));
        mAdapter = new TreeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnScrollToListener(new OnScrollToListener() {
            @Override
            public void scrollTo(int position) {
                mRecyclerView.scrollToPosition(position);
            }
        });
        if (REQUEST_CODE_TEACHER == mRequestCode){//只获取教室列表数据
            mPresenter.getListTeacher();
        } else {//获取所有用户数据
            mPresenter.getListRecipient();
        }
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

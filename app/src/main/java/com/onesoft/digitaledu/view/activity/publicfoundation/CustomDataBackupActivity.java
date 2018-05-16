package com.onesoft.digitaledu.view.activity.publicfoundation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.app.Constant;
import com.onesoft.digitaledu.model.DataBackup;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.presenter.common.CustomDataBackupPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.common.IDataBackupView;
import com.onesoft.digitaledu.widget.dialog.SelectRoleAdapter;

import java.util.List;

/**
 * 数据备份界面
 * Created by Jayden on 2016/11/23.
 */
public class CustomDataBackupActivity extends ToolBarActivity<CustomDataBackupPresenter> implements IDataBackupView {

    private ListView mListView;
    private ImageButton mUpBtn;

    private SelectRoleAdapter<DataBackup> mAdapter;
    private List<DataBackup> mDataBackups;

    public static void startDataBackUp(Context context, ListBean topDirectory) {
        Intent intent = new Intent(context, CustomDataBackupActivity.class);
        intent.putExtra("databackup", topDirectory);
        context.startActivity(intent);
    }

    private ListBean mTopDirectory;

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTopDirectory = (ListBean) getIntent().getExtras().getSerializable("databackup");
        }
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new CustomDataBackupPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_custom_data_backup, null);
    }

    @Override
    public void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mUpBtn = (ImageButton) findViewById(R.id.btn_up);

    }
    @Override
    public void initListener() {
        findViewById(R.id.btn_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builderName = new StringBuilder();
                StringBuilder idBuilderComment = new StringBuilder();
                for (DataBackup item : mDataBackups) {
                    if (item.isSelect) {
                        builderName.append(item.table_name).append("|");
                        idBuilderComment.append(item.table_comment).append(",");
                    }
                }
                if (builderName.length() > 0) {
                    String names = builderName.deleteCharAt(builderName.length() - 1).toString();
                    String comment = idBuilderComment.deleteCharAt(idBuilderComment.length() - 1).toString();
                    mPresenter.backupData(names, comment);
                }
            }
        });

        mUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.setSelection(0);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= Constant.VISIBLE_ITEM_TO_TOP) {
                    mUpBtn.setVisibility(View.VISIBLE);
                } else {
                    mUpBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    @Override
    public void initData() {
        if (mTopDirectory != null) {
            setTitle(mTopDirectory.name);
        }
        mAdapter = new SelectRoleAdapter<DataBackup>(this, true) {
            @Override
            public void bindView(int position, ViewHolder viewHolder) {
                final DataBackup item = (DataBackup) getItem(position);
                viewHolder.select.setSelected(item.isSelect);
                final ViewHolder finalViewHolder = viewHolder;
                viewHolder.select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//改变状态
                        if (finalViewHolder.select.isSelected()) {
                            finalViewHolder.select.setSelected(false);
                            item.isSelect = false;
                        } else {
                            finalViewHolder.select.setSelected(true);
                            item.isSelect = true;
                        }
                    }
                });
                viewHolder.name.setText(item.table_comment);
            }
        };
        mListView.setAdapter(mAdapter);

        mPresenter.getData();
    }

    @Override
    public void onSuccess(List<DataBackup> beans) {
        mDataBackups = beans;
        mAdapter.setDatas(beans);
        mPageStateLayout.onSucceed();
    }

    @Override
    public void onDataBackUPSuccess() {
        Toast.makeText(this, "Data BackUP Success", Toast.LENGTH_SHORT).show();
        finish();
    }
}

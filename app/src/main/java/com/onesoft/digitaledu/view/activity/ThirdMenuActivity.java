package com.onesoft.digitaledu.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.presenter.ThirdMenuPresenter;
import com.onesoft.digitaledu.view.activity.publicfoundation.CustomDataBackupActivity;
import com.onesoft.digitaledu.view.activity.publicfoundation.user.ListViewAdapter;
import com.onesoft.digitaledu.view.iview.IThirdView;

import java.util.List;

/**
 * 三级菜单界面
 * Created by Jayden on 2016/11/23.
 */
public class ThirdMenuActivity extends ToolBarActivity<ThirdMenuPresenter> implements IThirdView {

    private ListView mListView;
    private ListViewAdapter mAdapter;

    public static void startThirdMenu(Context context, TopDirectory topDirectory){
        Intent intent = new Intent(context,ThirdMenuActivity.class);
        intent.putExtra("twomenu",topDirectory);
        context.startActivity(intent);
    }

    private TopDirectory mTopDirectory;
    private void getDataFromForward(){
        if (getIntent() != null && getIntent().getExtras() != null){
            mTopDirectory = (TopDirectory) getIntent().getExtras().getSerializable("twomenu");
        }
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new ThirdMenuPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_listview, null);
    }

    @Override
    public void initView() {
        mListView = (ListView) findViewById(R.id.listview);
    }

    @Override
    public void initData() {
        if (mTopDirectory != null) {
            setTitle(mTopDirectory.name);
        }
        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        mPresenter.getData(mTopDirectory.id);
    }

    @Override
    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("387".equals(mAdapter.getItem(position).id)){//自定义数据备份
                    CustomDataBackupActivity.startDataBackUp(ThirdMenuActivity.this, mAdapter.getItem(position));
                } else {
                    ThirdToMainListActivity.startThirdToMainList(ThirdMenuActivity.this, mAdapter.getItem(position));
                }
            }
        });
    }

    @Override
    public void onSuccess(List<ListBean> beans) {
        mAdapter.setDatas(beans);
        mPageStateLayout.onSucceed();
    }
}

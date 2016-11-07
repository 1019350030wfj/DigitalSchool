package com.onesoft.digitaledu.view.activity.twolevel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.model.TwoLevelTitle;
import com.onesoft.digitaledu.presenter.twolevel.TwoLevelPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.fragment.home.TopDirectoryAdapter;
import com.onesoft.digitaledu.view.iview.twolevel.ITwoLevelView;

import java.util.List;

/**
 * Created by Jayden on 2016/10/29.
 */

public class TwoLevelActivity extends ToolBarActivity<TwoLevelPresenter> implements ITwoLevelView {

    public static final String PARAM = "params";

    public static void startTwoLevelActivity(Context context, TopDirectory topDirectory) {
        Intent intent = new Intent(context, TwoLevelActivity.class);
        intent.putExtra(PARAM, topDirectory);
        context.startActivity(intent);
    }

    private TopDirectory mTopDirectory;

    private void getDataFromForward() {
        mTopDirectory = (TopDirectory) getIntent().getExtras().getSerializable(PARAM);
    }

    private ListView mListView;
    private GridView mGridView;
    private TwoLevelTitleAdapter mTitleAdapter;
    private TopDirectoryAdapter mTopDirectoryAdapter;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new TwoLevelPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_two_level, null);
    }

    @Override
    public void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mGridView = (GridView) findViewById(R.id.gridView);
        mTitleAdapter = new TwoLevelTitleAdapter(this);
        mTopDirectoryAdapter = new TopDirectoryAdapter(this);
        mListView.setAdapter(mTitleAdapter);
        mGridView.setAdapter(mTopDirectoryAdapter);
    }

    @Override
    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTitle(mTitleAdapter.getDatas().get(position).name);
                mTopDirectoryAdapter.setDatas(mTitleAdapter.getDatas().get(position).mDirectories);
                mTitleAdapter.setSelectId(mTitleAdapter.getDatas().get(position).id);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getTwoLevelTitle(mTopDirectory.id);
        setTitle(mTopDirectory.name);
    }

    @Override
    public void onGetTwoLevel(List<TwoLevelTitle> twoLevelTitles) {
        mTitleAdapter.setDatas(twoLevelTitles);
        mTitleAdapter.setSelectId(twoLevelTitles.get(0).id);
        mTopDirectoryAdapter.setDatas(mTitleAdapter.getDatas().get(0).mDirectories);
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageStateLayout.onSucceed();
            }
        },4000);
    }
}

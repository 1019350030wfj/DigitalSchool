package com.onesoft.digitaledu.view.activity.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.DownloadBean;
import com.onesoft.digitaledu.presenter.person.DownloadedPresenter;
import com.onesoft.digitaledu.view.activity.person.download.DownloadedAdapter;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.view.iview.person.IDownloadedView;

import org.wlf.filedownloader.DownloadFileInfo;

import java.util.List;

/**
 * 下载文件
 * Created by Jayden on 2016/11/5.
 */

public class DownloadFileFragment extends BaseFragment<DownloadedPresenter> implements IDownloadedView {

    private ListView mListView;
    private DownloadedAdapter mAdapter;

    public static DownloadFileFragment newInstance(String s) {
        DownloadFileFragment newFragment = new DownloadFileFragment();
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
    protected void initPageState() {
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_download_now_empty, null, false);
        mPageStateLayout.setEmptyView(emptyView);
    }

    @Override
    public void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAdapter = new DownloadedAdapter(getActivity());
        mListView.setAdapter(mAdapter);

//        mPresenter.getDownloadData();
        mPageStateLayout.onSucceed();
    }

    @Override
    public void initListener() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((OfflineDownloadActivity) getActivity()).showDeleteMode();//长按是删除模式
                setBoxAdapterDeleteMode(true);
                ((OfflineDownloadActivity) getActivity()).updateDeleteNum("0");
                return true;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }


    private boolean mIsDeleteMode;

    public void setBoxAdapterDeleteMode(boolean isDeleteMode) {
        mIsDeleteMode = isDeleteMode;
        mAdapter.setISDeleteMode(isDeleteMode);
    }

    public void setSelectAll(boolean isSelectAll) {//全选
        if (mIsDeleteMode) {
            for (DownloadFileInfo boxBean : mAdapter.getDatas()) {
                boxBean.isDelete = isSelectAll;
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void initPresenter() {
        mPageStateLayout.onLoading();
        mPresenter = new DownloadedPresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(List<DownloadBean> boxBeanList) {
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageStateLayout.onSucceed();
            }
        }, 2000);
    }
}

package com.onesoft.digitaledu.view.activity.person.download;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.person.DownloadNowPresenter;
import com.onesoft.digitaledu.view.activity.person.OfflineDownloadActivity;
import com.onesoft.digitaledu.view.fragment.BaseFragment;
import com.onesoft.digitaledu.view.iview.person.IDownloadNowView;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnDeleteDownloadFilesListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 正在下载
 * Created by Jayden on 2016/11/5.
 */

public class DownloadingFileFragment extends BaseFragment<DownloadNowPresenter> implements IDownloadNowView {

    private ListView mListView;
    private DownloadingAdapter mAdapter;

    public static DownloadingFileFragment newInstance(String s) {
        DownloadingFileFragment newFragment = new DownloadingFileFragment();
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
        mAdapter = new DownloadingAdapter(getActivity());
        mAdapter.setDownloadingListener(new DownloadingAdapter.OnDownloadingListener() {
            @Override
            public void onComplete() {//更新已经下载完列表
                ((OfflineDownloadActivity) getActivity()).updateShow();
                showPageState();//更新本页面
            }

            @Override
            public void onDelete() {
                ((OfflineDownloadActivity) getActivity()).updateDeleteNum();
            }
        });
        mListView.setAdapter(mAdapter);
        FileDownloader.registerDownloadStatusListener(mAdapter);//监听下载状态的变化
        showPageState();
    }

    public void showPageState() {
        if (mAdapter.getDatas() != null && mAdapter.getDatas().size() > 0) {
            mPageStateLayout.onSucceed();
        } else {
            mPageStateLayout.onEmpty();
        }
    }

    @Override
    public void initListener() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((OfflineDownloadActivity) getActivity()).showDeleteMode();//长按是删除模式
                setBoxAdapterDeleteMode(true);
                ((OfflineDownloadActivity) getActivity()).updateDeleteNum();
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
            for (DownloadFileInfo boxBean : FileDownloader.getDownloadFiles()) {
                boxBean.isDelete = isSelectAll;
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void initPresenter() {
        mPageStateLayout.onLoading();
        mPresenter = new DownloadNowPresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(final List<String> urls) {
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                FileDownloader.start(urls);
                mPageStateLayout.onSucceed();
            }
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // pause all downloads
        FileDownloader.pauseAll();
        // unregisterDownloadStatusListener
        FileDownloader.unregisterDownloadStatusListener(mAdapter);
    }

    public void delete() {//删除
        if (mIsDeleteMode) {//是删除模式才做删除操作
            List<String> deleteUrls = new ArrayList<>();
            for (DownloadFileInfo boxBean : mAdapter.getDatas()) {
                if (boxBean.isDelete) {//需要删除的
                    deleteUrls.add(boxBean.getUrl());
                }
            }
            if (deleteUrls.size() > 0) {
                FileDownloader.delete(deleteUrls, true, new OnDeleteDownloadFilesListener() {
                    @Override
                    public void onDeleteDownloadFilesPrepared(List<DownloadFileInfo> downloadFilesNeedDelete) {

                    }

                    @Override
                    public void onDeletingDownloadFiles(List<DownloadFileInfo> downloadFilesNeedDelete, List<DownloadFileInfo> downloadFilesDeleted, List<DownloadFileInfo> downloadFilesSkip, DownloadFileInfo downloadFileDeleting) {

                    }

                    @Override
                    public void onDeleteDownloadFilesCompleted(List<DownloadFileInfo> downloadFilesNeedDelete, List<DownloadFileInfo> downloadFilesDeleted) {
                        mAdapter.updateShow();
                        showPageState();
                        ((OfflineDownloadActivity) getActivity()).updateDeleteNum();
                    }
                });
            }
        }
    }

    public List<DownloadFileInfo> getDatas() {
        if (mAdapter != null) {
            return mAdapter.getDatas();
        }
        return null;
    }
}

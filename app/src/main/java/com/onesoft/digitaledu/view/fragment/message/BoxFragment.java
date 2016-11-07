package com.onesoft.digitaledu.view.fragment.message;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.view.iview.message.IInBoxView;
import com.onesoft.digitaledu.presenter.message.InBoxPresenter;
import com.onesoft.digitaledu.view.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Jayden on 2016/11/5.
 */

public class BoxFragment extends BaseFragment<InBoxPresenter> implements IInBoxView {

    private ListView mListView;
    private BoxAdapter mBoxAdapter;

    public static BoxFragment newInstance(String s) {
        BoxFragment newFragment = new BoxFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        //bundle还可以在每个标签里传送数据
        return newFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_inbox;
    }

    @Override
    public void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBoxAdapter = new BoxAdapter(getActivity());
        mListView.setAdapter(mBoxAdapter);
        mPresenter.getInBoxData();
    }

    @Override
    public void initListener() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((MessageFragment) getParentFragment()).showDeleteMode();//长按是删除模式
                setBoxAdapterDeleteMode(true);
                ((MessageFragment) getParentFragment()).updateDeleteNum("0");
                return true;
            }
        });
    }


    private boolean mIsDeleteMode;
    public void setBoxAdapterDeleteMode(boolean isDeleteMode) {
        mIsDeleteMode = isDeleteMode;
        mBoxAdapter.setISDeleteMode(isDeleteMode);
    }

    public void setSelectAll() {//全选
        if (mIsDeleteMode){
            for (BoxBean boxBean :  mBoxAdapter.getDatas()){
                boxBean.isDelete = true;
                mBoxAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter = new InBoxPresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(List<BoxBean> boxBeanList) {
        mBoxAdapter.setDatas(boxBeanList);
    }
}

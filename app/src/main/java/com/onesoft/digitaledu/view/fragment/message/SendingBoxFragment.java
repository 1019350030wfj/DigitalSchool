package com.onesoft.digitaledu.view.fragment.message;

import android.os.Bundle;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.view.iview.message.ISendingBoxView;
import com.onesoft.digitaledu.presenter.message.SendingBoxPresenter;
import com.onesoft.digitaledu.view.fragment.BaseFragment;

/**
 * Created by Jayden on 2016/11/5.
 */
public class SendingBoxFragment extends BaseFragment<SendingBoxPresenter> implements ISendingBoxView {

    public static SendingBoxFragment newInstance(String s) {
        SendingBoxFragment newFragment = new SendingBoxFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        //bundle还可以在每个标签里传送数据
        return newFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sending_box;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new SendingBoxPresenter(getActivity(), this);
    }
}

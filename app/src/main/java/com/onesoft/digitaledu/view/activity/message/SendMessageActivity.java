package com.onesoft.digitaledu.view.activity.message;

import android.view.LayoutInflater;
import android.view.View;

import com.onesoft.digitaledu.presenter.message.SendMessagePresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.message.ISendMessageView;

/**
 * Created by Jayden on 2016/11/7.
 */

public class SendMessageActivity extends ToolBarActivity<SendMessagePresenter> implements ISendMessageView{

    @Override
    protected void initPresenter() {
        mPresenter = new SendMessagePresenter(this,this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return null;
    }
}

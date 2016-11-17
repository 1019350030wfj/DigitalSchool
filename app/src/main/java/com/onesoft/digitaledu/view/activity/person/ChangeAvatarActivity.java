package com.onesoft.digitaledu.view.activity.person;

import android.view.LayoutInflater;
import android.view.View;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.person.ChangeAvatarPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.IChangeAvatarView;

/**
 * Created by Jayden on 2016/11/14.
 */

public class ChangeAvatarActivity extends ToolBarActivity<ChangeAvatarPresenter> implements IChangeAvatarView {
    @Override
    protected void initPresenter() {
        mPresenter =new  ChangeAvatarPresenter(this,this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_change_avatar,null);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        hideToolbar();
        hideTitleLine();
    }

    @Override
    public void initData() {
        mPageStateLayout.onSucceed();
    }

    public void onCancel(View view){
        finish();
//        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_exit);
    }

}

package com.onesoft.digitaledu.view.activity.person;

import android.view.LayoutInflater;
import android.view.View;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.person.CopyrightPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.ICopyrightView;

/**
 * Created by Jayden on 2016/11/16.
 */

public class CopyrightActivity extends ToolBarActivity<CopyrightPresenter> implements ICopyrightView {
    @Override
    protected void initPresenter() {
        mPresenter = new CopyrightPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_copyright, null);
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.copyright_renewal));
        mPageStateLayout.onSucceed();
    }
}

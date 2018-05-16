package com.onesoft.digitaledu.view.activity.publicfoundation.department;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.user.UserAddPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.user.IUserAddView;

/**
 * Created by Jayden on 2016/11/23.
 */

public class SubordinateAddActivity extends ToolBarActivity<UserAddPresenter> implements IUserAddView {

    @Override
    protected void initPresenter() {
        mPresenter = new UserAddPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_subordination_add, null);
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.add_subordinate_relation));
        mPageStateLayout.onSucceed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpaper_setting, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_finish);
        View view = LayoutInflater.from(this).inflate(R.layout.menu_setting_wallpaper, null);
        TextView textView = (TextView) view.findViewById(R.id.riv_menu_main);
        textView.setText(getString(R.string.submit));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
    }
}

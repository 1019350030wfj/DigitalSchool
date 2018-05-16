package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.Template10DynamicPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.ITemplate10DynamicView;
import com.onesoft.digitaledu.widget.LayoutListView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_TO_MAIN;

/**
 * 模版10，校区管理  添加  动态模版
 * Created by Jayden on 2016/12/15.
 */

public class Template10AddDynamicActivity extends ToolBarActivity<Template10DynamicPresenter> implements ITemplate10DynamicView {

    @BindView(R.id.listview)
    LayoutListView mListview;
    private TopBtn mTopBtn;

    private Template10Adapter mTemplate10Adapter;

    public static void startTemplate10(Context context, TopBtn topBtn) {
        Intent intent = new Intent(context, Template10AddDynamicActivity.class);
        intent.putExtra("topbtn", topBtn);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTopBtn = (TopBtn) getIntent().getExtras().getSerializable("topbtn");
        }
    }


    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new Template10DynamicPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_template_10_dynamic, null);
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
                mPresenter.addTemplate10(mTopBtn);
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void initData() {
        setTitle(mTopBtn.template_name);
        mTemplate10Adapter = new Template10Adapter(this);
        mTemplate10Adapter.setDatas(mTopBtn.mTemplate10Been);
        mListview.setAdapter(mTemplate10Adapter);
        mPageStateLayout.onSucceed();
        if (!TextUtils.isEmpty(mTopBtn.notice)){//判断是否需要提醒
            RemindUtils.showDialog(this,mTopBtn.notice);
        }
    }

    @Override
    public void onAddSuccess() {
        EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_TO_MAIN, ""));//刷新动态列表
        finish();
    }
}

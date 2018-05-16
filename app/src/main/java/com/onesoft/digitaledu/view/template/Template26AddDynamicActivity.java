package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.Template27DynamicPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder25;
import com.onesoft.digitaledu.widget.LayoutListView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_MAIN_TO_LIST;
import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_TO_MAIN;

/**
 * 添加  动态模版
 * Created by Jayden on 2016/12/20.
 */

public class Template26AddDynamicActivity extends ToolBarActivity<Template27DynamicPresenter> implements IAddView {

    @BindView(R.id.listview)
    LayoutListView mListview;

    private Template26Adapter mTemplate26Adapter;

    private ThirdToMainBean mKeyValueBean;
    private TopBtn mTopBtn;

    public static void startTemplate(Context context, ThirdToMainBean keyValueBean, TopBtn topBtn) {
        Intent intent = new Intent(context, Template26AddDynamicActivity.class);
        intent.putExtra("topbtn", topBtn);
        intent.putExtra("keyvalue", keyValueBean);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mKeyValueBean = (ThirdToMainBean) getIntent().getExtras().getSerializable("keyvalue");
            mTopBtn = (TopBtn) getIntent().getExtras().getSerializable("topbtn");
        }
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new Template27DynamicPresenter(this, this);
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
                mPresenter.addTemplate(mKeyValueBean, mTopBtn);
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
        mTemplate26Adapter = new Template26Adapter(this);
        mTemplate26Adapter.setTopBtn(mTopBtn);
        mTemplate26Adapter.setDatas(mTopBtn.mTemplate10Been);
        mListview.setAdapter(mTemplate26Adapter);
        mPageStateLayout.onSucceed();
        if (!TextUtils.isEmpty(mTopBtn.notice)) {//判断是否需要提醒
            RemindUtils.showDialog(this, mTopBtn.notice);
        }
    }

    @Override
    public void onAddSuccess() {
        EventBus.getDefault().post(new BaseEvent(mKeyValueBean == null ? UPDATE_THIRD_TO_MAIN : UPDATE_THIRD_MAIN_TO_LIST, ""));//刷新动态列表
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ViewHolder25.REQUEST_FILE && data != null && mTemplate26Adapter != null) {//获取文件返回的结果
                EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_FILE_SELECT, data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)));
            }
        }
    }
}

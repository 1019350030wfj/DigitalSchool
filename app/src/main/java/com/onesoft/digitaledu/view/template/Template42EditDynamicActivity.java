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
import com.onesoft.digitaledu.presenter.template.Template42EditDynamicPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.IEditView;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder25;
import com.onesoft.digitaledu.widget.LayoutListView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_MAIN_TO_LIST;
import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_TO_MAIN;

/**
 * 如果是从动态页过来的，在编辑成功时请看 onEditSuccess()方法
 * 已分配权限编辑  动态模版
 * Created by Jayden on 2016/12/15.
 */

public class Template42EditDynamicActivity extends ToolBarActivity<Template42EditDynamicPresenter> implements IEditView {

    @BindView(R.id.listview)
    LayoutListView mListview;

    private Template42Adapter mTemplate42Adapter;
    private ThirdToMainBean mKeyValueBean;
    private TopBtn mTopBtn;

    public static void startTemplate(Context context, ThirdToMainBean keyValueBean, TopBtn topBtn) {
        Intent intent = new Intent(context, Template42EditDynamicActivity.class);
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
        mPresenter = new Template42EditDynamicPresenter(this, this);
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
            public void onClick(View v) { //提交
                mPresenter.modify(mKeyValueBean, mTopBtn);
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
        mTemplate42Adapter = new Template42Adapter(this);
        mTemplate42Adapter.setTopBtn(mTopBtn);
        mTemplate42Adapter.setDatas(mTopBtn.mTemplate10Been);
        mListview.setAdapter(mTemplate42Adapter);
        mPageStateLayout.onSucceed();
        if (!TextUtils.isEmpty(mTopBtn.notice)) {//判断是否需要提醒
            RemindUtils.showDialog(this, mTopBtn.notice);
        }
    }

    @Override
    public void onEditSuccess() {
        if ("98".equals(mTopBtn.template_id) || "79".equals(mTopBtn.template_id)) {//教室 编辑
            EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_MAIN_TO_LIST, ""));
        } else {
            EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_TO_MAIN, ""));//刷新动态列表
        }
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ViewHolder25.REQUEST_FILE && data != null && mTemplate42Adapter != null) {//获取文件返回的结果
                EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_FILE_SELECT, data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)));
            }
        }
    }
}

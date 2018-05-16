package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.Template27Presenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.IEditView;

import org.greenrobot.eventbus.EventBus;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_TO_MAIN;

/**
 * 模版27  用户编辑
 * Created by Jayden on 2016/11/12.
 */
public class Template27Activity extends ToolBarActivity<Template27Presenter> implements IEditView {

    private TextView mEtOldPwd;
    private EditText  mEtNewPwd, mEtConfirmPwd;

    private ThirdToMainBean mKeyValueBean;
    private TopBtn mTopBtn;

    public static void startTemplate(Context context, ThirdToMainBean keyValueBean, TopBtn topBtn) {
        Intent intent = new Intent(context, Template27Activity.class);
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
        mPresenter = new Template27Presenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_template_27, null);
    }

    @Override
    public void initView() {
        mEtOldPwd = (TextView) findViewById(R.id.et_old_pwd);
        mEtNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        mEtConfirmPwd = (EditText) findViewById(R.id.et_confirm_pwd);
    }

    @Override
    public void initData() {
        setTitle(mTopBtn.template_name);
        mEtOldPwd.setText(mTopBtn.mClientUser.get("user_name").originValue.toString());
        mPageStateLayout.onSucceed();
        if (!TextUtils.isEmpty(mTopBtn.notice)){//判断是否需要提醒
            RemindUtils.showDialog(this,mTopBtn.notice);
        }
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
                String newPwd = mEtNewPwd.getText().toString().trim();
                String confirmPwd = mEtConfirmPwd.getText().toString().trim();
                if (TextUtils.isEmpty(newPwd)){
                    RemindUtils.showDialog(Template27Activity.this, "新密码不能为空！");
                } else if (TextUtils.isEmpty(confirmPwd)){
                    RemindUtils.showDialog(Template27Activity.this, "确认密码不能为空！");
                } else if (!newPwd.equals(confirmPwd)){
                    RemindUtils.showDialog(Template27Activity.this, "两次密码不一样！");
                } else {
                    mPresenter.modify(mKeyValueBean, mTopBtn, newPwd);
                }

            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onEditSuccess() {
        EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_TO_MAIN, ""));//刷新动态列表
        finish();
    }
}

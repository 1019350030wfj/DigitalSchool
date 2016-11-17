package com.onesoft.digitaledu.view.activity.person;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.person.ModifyPwdPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.IModifyPwdView;

/**
 * Created by Jayden on 2016/11/12.
 */
public class ModifyPwdActivity extends ToolBarActivity<ModifyPwdPresenter> implements IModifyPwdView {

    private TextView mTvOldPwd, mTvNewPwd, mTvConfirmPwd;
    private EditText mEtOldPwd, mEtNewPwd, mEtConfirmPwd;
    private Button mBtnModify;

    @Override
    protected void initPresenter() {
        mPresenter = new ModifyPwdPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_modify_pwd, null);
    }

    @Override
    public void initView() {
        mTvOldPwd = (TextView) findViewById(R.id.tv_old_pwd);
        mTvNewPwd = (TextView) findViewById(R.id.tv_new_pwd);
        mTvConfirmPwd = (TextView) findViewById(R.id.tv_confirm_pwd);
        mEtOldPwd = (EditText) findViewById(R.id.et_old_pwd);
        mEtNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        mEtConfirmPwd = (EditText) findViewById(R.id.et_confirm_pwd);
        mBtnModify = (Button) findViewById(R.id.btn_confirm);
        setTitle(getString(R.string.modify_pwd));
        mPageStateLayout.onSucceed();
    }

    @Override
    public void initListener() {
        mBtnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPwd = mEtOldPwd.getText().toString().trim();
                String newPwd = mEtNewPwd.getText().toString().trim();
                String confirmPwd = mEtConfirmPwd.getText().toString().trim();
                mPresenter.modify(ModifyPwdActivity.this, oldPwd, newPwd, confirmPwd);
            }
        });
    }

    @Override
    public void emptyNewPwd() {
        RemindUtils.showDialog(this, "新密码不能为空！");
    }

    @Override
    public void emptyOldPwd() {
        RemindUtils.showDialog(this, "原先密码不能为空！");
    }

    @Override
    public void emptyConfirmPwd() {
        RemindUtils.showDialog(this, "确认密码不能为空！");
    }

    @Override
    public void noSamePwd() {
        RemindUtils.showDialog(this, "两次输入不一致！");
    }

    @Override
    public void oldPwdError() {
        RemindUtils.showDialog(this, "原密码不正确！");
    }

    @Override
    public void modifySuccess() {
        RemindUtils.showDialog(this, "重置密码成功！");
    }
}

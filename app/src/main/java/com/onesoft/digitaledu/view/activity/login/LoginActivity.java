package com.onesoft.digitaledu.view.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onesoft.digitaledu.BuildConfig;
import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.LoginBean;
import com.onesoft.digitaledu.presenter.login.LoginPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.activity.MainActivity;
import com.onesoft.digitaledu.view.iview.login.ILoginView;
import com.onesoft.digitaledu.widget.CircleImageView;
import com.onesoft.netlibrary.utils.ImageHandler;


/**
 * Created by Jayden on 2016/9/7.
 */
public class LoginActivity extends Activity implements ILoginView {

    private EditText mEditUser;
    private EditText mEditPwd;
    private Button mBtnLogin;
    private Button mBtnForgetPwd;
    private CircleImageView mIvAvater;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnForgetPwd = (Button) findViewById(R.id.btn_forget_pwd);
        mEditUser = (EditText) findViewById(R.id.edi_phone);
        mEditPwd = (EditText) findViewById(R.id.edi_code);
        mIvAvater = (CircleImageView) findViewById(R.id.iv_avater);
        if (BuildConfig.DEBUG) {
//            mEditUser.setText("t00001");
//            mEditPwd.setText("123456");
            mEditUser.setText("administrator");
            mEditPwd.setText("111111");
        }
        mPresenter = new LoginPresenter(this);

        initListener();
    }

    private void initListener() {
        mEditUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.getAvatar(LoginActivity.this, s.toString());
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这边是点击登录要做的事情
                mBtnLogin.setEnabled(false);
                mPresenter.login(LoginActivity.this, mEditUser.getText().toString().trim(), mEditPwd.getText().toString().trim());
            }
        });

        mBtnForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void avatarSuccess(LoginBean loginBean) {
        ImageHandler.getAvater(this, mIvAvater, loginBean.info.get(0).photo);
    }

    @Override
    public void loginSuccess(final LoginBean loginBean) {
        mBtnLogin.setEnabled(true);
        //登录成功，设置isFirst= false
        SPHelper.setIsLogin(this, true);
        SPHelper.setUserId(this, loginBean.info.get(0).user_id);
        SPHelper.setMappedId(this, loginBean.info.get(0).mapped_id);
        SPHelper.setUserName(this, loginBean.info.get(0).real_name);
        SPHelper.setUserRole(this, loginBean.info.get(0).user_role);
        SPHelper.setUserType(this, loginBean.info.get(0).user_type);
        SPHelper.setCourseTable(this, loginBean.info.get(0).course_table);
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    @Override
    public void loginError(String errorMsg) {
        mBtnLogin.setEnabled(true);
        RemindUtils.showDialog(this, errorMsg);
    }

    @Override
    public void emptyPwd() {
        mBtnLogin.setEnabled(true);
        RemindUtils.showDialog(this, "请输入密码");
    }

    @Override
    public void emptyUserName() {
        mBtnLogin.setEnabled(true);
        RemindUtils.showDialog(this, "请输入账号");
    }
}

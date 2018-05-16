package com.onesoft.digitaledu.view.activity.person;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.PersonEvent;
import com.onesoft.digitaledu.model.PersonInfo;
import com.onesoft.digitaledu.presenter.person.PersonEditPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.IEditPersonView;
import com.onesoft.digitaledu.widget.dialog.SelectMessageDialog;
import com.onesoft.netlibrary.utils.ImageHandler;

import org.greenrobot.eventbus.EventBus;

/**
 * 个人信息
 * <p>
 * Created by Jayden on 2017/01/04.
 */
public class PersonInfoActivity extends ToolBarActivity<PersonEditPresenter> implements IEditPersonView {

    private static final int REQUEST_CHANGE_AVATAR = 0x34;

    private TextView mTxtDepartment;
    private TextView mTxtPlaceOrigin;
    private TextView mTxtOption;
    private TextView mTxtBloodType;
    private TextView mTxtBirthday;
    private TextView mTxtIdCard;
    private TextView mTxtPhone;
    private TextView mTxtHome;

    private EditText mEditEmail;
    private EditText mEditMotto;
    private EditText mEditHobby;
    private EditText mEditspecialty;

    private TextView mTxtSex;
    private TextView mTxtEdit;
    private TextView mTxtName;
    private TextView mTxtNumber;
    private TextView mTxtPosition;
    private ImageView mImgSex;
    private ImageView mImgAvatar;

    private View mViewPosition;
    private View mRlPosition;

    private PersonInfo mPersonInfo;

    private int mSelectMessageType = SelectMessageDialog.TYPE_NOTICE;

    @Override
    protected void initPresenter() {
        mPresenter = new PersonEditPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_person_info, null);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        hideToolbar();
        hideTitleLine();
    }

    @Override
    public void initView() {
        mImgSex = (ImageView) findViewById(R.id.img_sex);
        mImgSex.setVisibility(View.INVISIBLE);
        mImgAvatar = (ImageView) findViewById(R.id.iv_avater);
        mTxtSex = (TextView) findViewById(R.id.edit_sex);
        mTxtDepartment = (TextView) findViewById(R.id.edit_departmentn);
        mTxtEdit = (TextView) findViewById(R.id.tv_title);
        mTxtName = (TextView) findViewById(R.id.txt_name);
        mTxtNumber = (TextView) findViewById(R.id.edit_number);
        mTxtPosition = (TextView) findViewById(R.id.edit_position);
        mTxtPlaceOrigin = (TextView) findViewById(R.id.edit_place_of_origin);
        mTxtOption = (TextView) findViewById(R.id.edit_nation);
        mTxtBloodType = (TextView) findViewById(R.id.edit_blood_type);
        mTxtBirthday = (TextView) findViewById(R.id.edit_birthday);
        mTxtIdCard = (TextView) findViewById(R.id.edit_idcard);
        mTxtPhone = (TextView) findViewById(R.id.edit_phone_number);
        mTxtHome = (TextView) findViewById(R.id.edit_place);
        mEditEmail = (EditText) findViewById(R.id.edit_email);
        mEditMotto = (EditText) findViewById(R.id.edit_motto);
        mEditHobby = (EditText) findViewById(R.id.edit_hobby);
        mEditspecialty = (EditText) findViewById(R.id.edit_specialty);
        mViewPosition = findViewById(R.id.view_position);
        mRlPosition = findViewById(R.id.rl_position);
    }

    @Override
    public void initListener() {
        findViewById(R.id.iv_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeAvatarActivity.startChangeAvatar(PersonInfoActivity.this, mPersonInfo.photo, REQUEST_CHANGE_AVATAR);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_exit);
            }
        });
        mImgSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectMessageDialog dialog = new SelectMessageDialog(PersonInfoActivity.this);
                dialog.show();
                dialog.setSelect(mSelectMessageType);
                dialog.setText(getString(R.string.man), getString(R.string.woman));
                dialog.setSelectTypeListener(new SelectMessageDialog.ISelectMsgType() {
                    @Override
                    public void onMessage() {
                        mSelectMessageType = SelectMessageDialog.TYPE_MESSAGE;
                        mTxtSex.setText(getString(R.string.woman));
                    }

                    @Override
                    public void onNotice() {
                        mSelectMessageType = SelectMessageDialog.TYPE_NOTICE;
                        mTxtSex.setText(getString(R.string.man));
                    }
                });
            }
        });
        mTxtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getString(R.string.edit).equals(mTxtEdit.getText())) {//点击编辑变成完成
                    mTxtEdit.setText(getString(R.string.finish));
                    mEditEmail.setFocusable(true);
                    mEditHobby.setFocusable(true);
                    mEditMotto.setFocusable(true);
                    mEditspecialty.setFocusable(true);
                    mEditEmail.setFocusableInTouchMode(true);
                    mEditHobby.setFocusableInTouchMode(true);
                    mEditMotto.setFocusableInTouchMode(true);
                    mEditspecialty.setFocusableInTouchMode(true);
                    mEditEmail.requestFocus();
                } else {
                    mTxtEdit.setText(getString(R.string.edit));//post到服务器
                    mEditEmail.setFocusable(false);
                    mEditHobby.setFocusable(false);
                    mEditMotto.setFocusable(false);
                    mEditspecialty.setFocusable(false);
                    mPresenter.editInfo(mEditEmail.getText().toString().trim(), mEditMotto.getText().toString().trim()
                            , mEditHobby.getText().toString().trim(), mEditspecialty.getText().toString().trim());
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        findViewById(R.id.rl_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonInfoActivity.startModify(PersonInfoActivity.this, ModifyPersonInfoActivity.MODIFY_EMAIL, mPersonInfo.email);
            }
        });
        findViewById(R.id.rl_motto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonInfoActivity.startModify(PersonInfoActivity.this, ModifyPersonInfoActivity.MODIFY_MOTTO, mPersonInfo.motto);
            }
        });
        findViewById(R.id.rl_hobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonInfoActivity.startModify(PersonInfoActivity.this, ModifyPersonInfoActivity.MODIFY_HOBBY, mPersonInfo.habby);
            }
        });
        findViewById(R.id.rl_specialty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonInfoActivity.startModify(PersonInfoActivity.this, ModifyPersonInfoActivity.MODIFY_SPECIALTY, mPersonInfo.specialty);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getPersonInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CHANGE_AVATAR && data != null) {
            ImageHandler.getAvater(this, mImgAvatar, data.getStringExtra("data"));
            mPersonInfo.photo = data.getStringExtra("data");
            EventBus.getDefault().post(new PersonEvent(0, mPersonInfo));
        } else if (resultCode == RESULT_OK && requestCode == ModifyPersonInfoActivity.MODIFY_EMAIL && data != null) {
            mPersonInfo.email = data.getStringExtra("data");
            mEditEmail.setText(mPersonInfo.email);
            EventBus.getDefault().post(new PersonEvent(0, mPersonInfo));
        } else if (resultCode == RESULT_OK && requestCode == ModifyPersonInfoActivity.MODIFY_MOTTO && data != null) {
            mPersonInfo.motto = data.getStringExtra("data");
            mEditMotto.setText(mPersonInfo.motto);
            EventBus.getDefault().post(new PersonEvent(0, mPersonInfo));
        } else if (resultCode == RESULT_OK && requestCode == ModifyPersonInfoActivity.MODIFY_HOBBY && data != null) {
            mPersonInfo.habby = data.getStringExtra("data");
            mEditHobby.setText(mPersonInfo.habby);
            EventBus.getDefault().post(new PersonEvent(0, mPersonInfo));
        } else if (resultCode == RESULT_OK && requestCode == ModifyPersonInfoActivity.MODIFY_SPECIALTY && data != null) {
            mPersonInfo.specialty = data.getStringExtra("data");
            mEditspecialty.setText(mPersonInfo.specialty);
            EventBus.getDefault().post(new PersonEvent(0, mPersonInfo));
        }
    }

    @Override
    public void onSuccess() {
        //修改成功
        mPersonInfo.email = mEditEmail.getText().toString().trim();
        mPersonInfo.motto = mEditMotto.getText().toString().trim();
        mPersonInfo.habby = mEditHobby.getText().toString().trim();
        mPersonInfo.specialty = mEditspecialty.getText().toString().trim();
        EventBus.getDefault().post(new PersonEvent(0, mPersonInfo));
    }

    @Override
    public void onSuccess(PersonInfo personInfo) {
        if (personInfo != null) {
            mPersonInfo = personInfo;
            ImageHandler.getAvater(this, mImgAvatar, mPersonInfo.photo);
            mTxtName.setText(mPersonInfo.real_name);
            mTxtNumber.setText(mPersonInfo.user_name);
            mTxtSex.setText("1".equals(mPersonInfo.sex) ? getString(R.string.man) : getString(R.string.woman));
            if ("2".equals(mPersonInfo.user_type)) {//学生
                mViewPosition.setVisibility(View.GONE);
                mRlPosition.setVisibility(View.GONE);
                mTxtPlaceOrigin.setText(mPersonInfo.koseki);
            } else {//教师
                mTxtPosition.setText(mPersonInfo.jobtitle);
                mTxtPlaceOrigin.setText(mPersonInfo.province);
            }
            mTxtDepartment.setText(mPersonInfo.depart_name);
            mTxtOption.setText(mPersonInfo.nationid_name);
            mTxtBloodType.setText(mPersonInfo.bloodtype);
            mTxtBirthday.setText(mPersonInfo.birthday);
            mTxtIdCard.setText(mPersonInfo.idcard);
            mTxtPhone.setText(mPersonInfo.mobilephone);
            mTxtHome.setText(mPersonInfo.address);

            mEditEmail.setText(mPersonInfo.email);
            mEditMotto.setText(mPersonInfo.motto);
            mEditHobby.setText(mPersonInfo.habby);
            mEditspecialty.setText(mPersonInfo.specialty);
            mPageStateLayout.onSucceed();
        } else {
            mPageStateLayout.onEmpty();
        }
    }
}

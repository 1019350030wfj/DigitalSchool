package com.onesoft.digitaledu.view.activity.infomanager.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.PersonContact;
import com.onesoft.digitaledu.presenter.infomanager.contacts.PersonContactInfoPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IPersonContactInfoView;
import com.onesoft.digitaledu.widget.CircleImageView;
import com.onesoft.netlibrary.utils.ImageHandler;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.onesoft.digitaledu.R.id.edit_position;
import static com.onesoft.digitaledu.view.activity.infomanager.contacts.ModifyPersonContactInfoActivity.MODIFY_NAME;
import static com.onesoft.digitaledu.view.activity.infomanager.contacts.ModifyPersonContactInfoActivity.MODIFY_REMARK;
import static com.onesoft.digitaledu.view.activity.infomanager.contacts.ModifyPersonContactInfoActivity.MODIFY_TEL;
import static com.onesoft.digitaledu.view.activity.infomanager.contacts.ModifyPersonContactInfoActivity.MODIFY_TEL_TYPE;

/**
 * 通讯录 个人通讯录 个人信息
 * Created by Jayden on 2016/11/14.
 */
public class ContactPersonInfoActivity extends ToolBarActivity<PersonContactInfoPresenter> implements IPersonContactInfoView {

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.iv_avater)
    CircleImageView mIvAvater;
    @BindView(R.id.edit_departmentn)
    TextView mEditDepartmentn;
    @BindView(edit_position)
    TextView mEditPosition;
    @BindView(R.id.edit_place_of_origin)
    TextView mEditPlaceOfOrigin;
    @BindView(R.id.edit_remark_info)
    TextView mEditRemark;
    @BindView(R.id.btn_sendMessage)
    Button mBtnSendMessage;

    private PersonContact mPersonContact;

    public static void startContactPersonInfo(PersonContact personContact, Context context) {
        Intent intent = new Intent(context, ContactPersonInfoActivity.class);
        intent.putExtra("info", personContact);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mPersonContact = (PersonContact) getIntent().getExtras().getSerializable("info");
        }
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new PersonContactInfoPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_contact_person_info, null);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        hideToolbar();
        hideTitleLine();
    }

    @Override
    public void initListener() {
        findViewById(R.id.iv_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBtnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//调用删除接口
                mPresenter.delete(mPersonContact.id);
            }
        });

        mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonContactInfoActivity.startModify(ContactPersonInfoActivity.this,
                        MODIFY_NAME,mPersonContact.id,mPersonContact.name);
            }
        });
        findViewById(R.id.rl_tel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonContactInfoActivity.startModify(ContactPersonInfoActivity.this,
                        MODIFY_TEL,mPersonContact.id,mPersonContact.phoneNumber);
            }
        });
        findViewById(R.id.rl_tel_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonContactInfoActivity.startModify(ContactPersonInfoActivity.this,
                        MODIFY_TEL_TYPE,mPersonContact.id,mPersonContact.type);
            }
        });
        findViewById(R.id.rl_remark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonContactInfoActivity.startModify(ContactPersonInfoActivity.this,
                        MODIFY_REMARK,mPersonContact.id,mPersonContact.remark);
            }
        });
    }


    @Override
    public void onDelSuccess() {
        EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_PERSON_CONTACT,""));
        finish();
    }

    @Override
    public void initData() {
        ImageHandler.getAvater(this,mIvAvater,mPersonContact.photo);
        mName.setText(mPersonContact.name);
        mEditDepartmentn.setText(mPersonContact.phoneNumber);
        mEditPosition.setText(mPersonContact.typename);
        mEditPlaceOfOrigin.setText(mPersonContact.add_date);
        mEditRemark.setText(mPersonContact.remark);

        mPageStateLayout.onSucceed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (resultCode == RESULT_OK && requestCode == MODIFY_NAME && data != null) {
           mPersonContact.name = data.getStringExtra("data");
           mName.setText(mPersonContact.name);
           EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_PERSON_CONTACT,""));
        } else if (resultCode == RESULT_OK && requestCode == MODIFY_TEL && data != null) {
           mPersonContact.phoneNumber = data.getStringExtra("data");
           mEditDepartmentn.setText(mPersonContact.phoneNumber);
           EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_PERSON_CONTACT,""));
        } else if (resultCode == RESULT_OK && requestCode == MODIFY_TEL_TYPE && data != null) {
           mPersonContact.type = data.getStringExtra("data");
           mPersonContact.typename = "1".equals(mPersonContact.type)?"手机":"固定电话";
           mEditPosition.setText(mPersonContact.typename);
           EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_PERSON_CONTACT,""));
        } else if (resultCode == RESULT_OK && requestCode == MODIFY_REMARK && data != null) {
           mPersonContact.remark = data.getStringExtra("data");
           mEditRemark.setText(mPersonContact.typename);
           EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_PERSON_CONTACT,""));
        }
    }

}

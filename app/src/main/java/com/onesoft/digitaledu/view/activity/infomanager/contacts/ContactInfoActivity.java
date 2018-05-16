package com.onesoft.digitaledu.view.activity.infomanager.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.SystemContact;
import com.onesoft.digitaledu.presenter.infomanager.contacts.SystemContactInfoPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.activity.message.SendMessageActivity;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.ISystemContactInfoView;
import com.onesoft.digitaledu.widget.CircleImageView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.netlibrary.utils.ImageHandler;

import butterknife.BindView;

/**
 * 通讯录 系统通讯录 个人信息
 * 这边也可以直接从treeItem中获取个人信息
 * 也可以根据id去服务器获取
 * Created by Jayden on 2016/11/14.
 */
public class ContactInfoActivity extends ToolBarActivity<SystemContactInfoPresenter> implements ISystemContactInfoView {

    @BindView(R.id.iv_avater)
    CircleImageView mIvAvater;
    @BindView(R.id.name)
    TextView mTextName;
    @BindView(R.id.edit_number)
    TextView mEditNumber;
    @BindView(R.id.edit_sex)
    TextView mEditSex;
    @BindView(R.id.edit_position)
    TextView mEditPosition;
    @BindView(R.id.edit_departmentn)
    TextView mEditDepartmentn;
    @BindView(R.id.edit_place_of_origin)
    TextView mEditPlaceOfOrigin;
    @BindView(R.id.edit_nation)
    TextView mEditNation;
    @BindView(R.id.btn_sendMessage)
    Button mBtnSendMessage;

    @BindView(R.id.view_depart)
    View mViewDepart;
    @BindView(R.id.rl_depart)
    View mRlDepart;

    private TreeItem mInfo;

    private void getDataFromForward() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mInfo = (TreeItem) intent.getExtras().getSerializable("info");
        }
    }

    public static void startInfoActivity(Context context, TreeItem data) {
        Intent intent = new Intent(context, ContactInfoActivity.class);
        intent.putExtra("info", data);
        context.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new SystemContactInfoPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_contact_info, null);
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
            public void onClick(View v) {
                SendMessageActivity.startSendMessageActivity(ContactInfoActivity.this,
                        SendMessageActivity.FROM_SYSTEM_CONTACT,mInfo.name,mInfo.id);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getPersonInfo(mInfo.id);
    }

    @Override
    public void onSuccess(SystemContact personInfo) {
        ImageHandler.getAvater(this, mIvAvater, personInfo.photo);
        mTextName.setText(personInfo.name);
        mEditNumber.setText(personInfo.the_teacher_id);
        if ("2".equals(personInfo.user_type)){//学生
            mRlDepart.setVisibility(View.GONE);
            mViewDepart.setVisibility(View.GONE);
        }
        mEditSex.setText(personInfo.depart_name);//所属院系
        mEditPosition.setText(personInfo.homephone);//办公电话
        mEditDepartmentn.setText(personInfo.mobilephone);//移动电话
        mEditPlaceOfOrigin.setText(personInfo.QQ);//QQ号
        mEditNation.setText(personInfo.address);//家庭地址
        mPageStateLayout.onSucceed();
    }
}

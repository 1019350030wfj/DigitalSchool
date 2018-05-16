package com.onesoft.digitaledu.view.activity.infomanager.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.presenter.infomanager.contacts.GroupContactInfoPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.infomanager.contacts.IPersonContactInfoView;
import com.onesoft.digitaledu.widget.CircleImageView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;
import com.onesoft.netlibrary.utils.ImageHandler;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * 通讯录 群组管理 个人信息
 * Created by Jayden on 2017/01/03.
 */
public class ContactGroupInfoActivity extends ToolBarActivity<GroupContactInfoPresenter> implements IPersonContactInfoView {

    @BindView(R.id.iv_avater)
    CircleImageView mIvAvater;
    @BindView(R.id.name)
    TextView mTxtName;
    @BindView(R.id.edit_number)
    TextView mEditNumber;
    @BindView(R.id.edit_position)
    TextView mEditPosition;
    @BindView(R.id.edit_departmentn)
    TextView mEditDepartmentn;
    @BindView(R.id.edit_place_of_origin)
    TextView mEditPlaceOfOrigin;
    @BindView(R.id.edit_nation)
    TextView mEditNation;
    private TreeItem mInfo;

    private void getDataFromForward() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mInfo = (TreeItem) intent.getExtras().getSerializable("info");
        }
    }

    public static void startInfoActivity(Context context, TreeItem data) {
        Intent intent = new Intent(context, ContactGroupInfoActivity.class);
        intent.putExtra("info", data);
        context.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new GroupContactInfoPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_contact_group_info, null);
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
        findViewById(R.id.btn_sendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//删除
                mPresenter.delete(mInfo.group_id, mInfo.id);
            }
        });
    }

    @Override
    public void initData() {
        mTxtName.setText(mInfo.name);
        ImageHandler.getAvater(this, mIvAvater, mInfo.imgUrl);
        mEditNumber.setText(mInfo.the_teacher_id);
        mEditPosition.setText(mInfo.homephone);
        mEditDepartmentn.setText(mInfo.mobilephone);
        mEditPlaceOfOrigin.setText(mInfo.QQ);
        mEditNation.setText(mInfo.address);
        mPageStateLayout.onSucceed();
    }

    @Override
    public void onDelSuccess() {//删除成功，更新群组列表
        EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_GROUP_CONTACT, ""));
        finish();
    }
}

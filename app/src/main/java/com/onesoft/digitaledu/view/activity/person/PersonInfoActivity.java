package com.onesoft.digitaledu.view.activity.person;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.person.PersonInfoPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.IPersonInfoView;
import com.onesoft.digitaledu.widget.dialog.SelectMessageDialog;

/**
 * Created by Jayden on 2016/11/14.
 */
public class PersonInfoActivity extends ToolBarActivity<PersonInfoPresenter> implements IPersonInfoView {

    private TextView mTxtSex;
    private ImageView mImgSex;
    private ImageView mImgAvatar;

    private int mSelectMessageType = SelectMessageDialog.TYPE_NOTICE;

    @Override
    protected void initPresenter() {
        mPresenter = new PersonInfoPresenter(this, this);
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
        mImgAvatar = (ImageView) findViewById(R.id.iv_avater);
        mTxtSex = (TextView) findViewById(R.id.edit_sex);
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
                startActivity(new Intent(PersonInfoActivity.this, ChangeAvatarActivity.class));
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
    }

    @Override
    public void initData() {
        mPageStateLayout.onSucceed();
    }
}

package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.TeacherInfo;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.Template16Presenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.activity.common.SelectTeacherActivity;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.digitaledu.widget.dialog.DistributionWayDialog;
import com.onesoft.digitaledu.widget.treerecyclerview.activity.TreeTemplate16Activity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_TO_MAIN;

/**
 * 模版16，添加权限下属
 * Created by Jayden on 2016/12/16.
 */

public class Template16Activity extends ToolBarActivity<Template16Presenter> implements IAddView {

    @BindView(R.id.txt_teacher_name)
    TextView mTxtTeacherName;
    @BindView(R.id.img_teacher_name)
    ImageView mImgTeacherName;
    @BindView(R.id.txt_sex)
    TextView mTxtSex;
    @BindView(R.id.img_distribution_way)
    ImageView mImgDistributionWay;
    @BindView(R.id.rl_sex)
    RelativeLayout mRlSex;
    @BindView(R.id.txt_subordinate_member)
    TextView mTxtSubordinateMember;
    @BindView(R.id.img_subordinate_member)
    ImageView mImgSubordinateMember;
    @BindView(R.id.edit_content)
    EditText mEditContent;

    public static final int REQUEST_SELECT_TEACHER = 0X123;
    public static final int REQUEST_SELECT_MEMBER = 0X124;
    private TeacherInfo mTeacherInfo;

    private ThirdToMainBean mKeyValueBean;
    private TopBtn mTopBtn;

    private int mSelectWay;//分配方式

    private String mChildTeacherIds = "";

    public static void startTemplate(Context context, ThirdToMainBean keyValueBean, TopBtn topBtn) {
        Intent intent = new Intent(context, Template16Activity.class);
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
        mPresenter = new Template16Presenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_template_16, null);
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
                mPresenter.add(mTeacherInfo.id,mSelectWay,mChildTeacherIds,
                        mEditContent.getText().toString().trim(),mTopBtn);
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
        mPageStateLayout.onSucceed();
        if (!TextUtils.isEmpty(mTopBtn.notice)){//判断是否需要提醒
            RemindUtils.showDialog(this,mTopBtn.notice);
        }
    }

    @Override
    public void initListener() {
        mImgTeacherName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Template16Activity.this, SelectTeacherActivity.class), REQUEST_SELECT_TEACHER);
            }
        });

        mImgDistributionWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistributionWayDialog dialog = new DistributionWayDialog(Template16Activity.this);
                dialog.show();
                dialog.setSelectTypeListener(new DistributionWayDialog.ISelectMsgType() {
                    @Override
                    public void onItemSelect(int type, String text) {
                        mSelectWay = type;
                        mTxtSex.setText(text);
                    }
                });
            }
        });

        mImgSubordinateMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//选择下属成员
                TreeTemplate16Activity.startRecipientActivity(Template16Activity.this, mSelectWay, REQUEST_SELECT_MEMBER);
            }
        });
    }

    @Override
    public void onAddSuccess() {
        EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_TO_MAIN, ""));//刷新动态列表
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_TEACHER && data != null) {
            mTeacherInfo = (TeacherInfo) data.getExtras().getSerializable("data");
            mTxtTeacherName.setText(mTeacherInfo.name);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_MEMBER && data != null) {
            mChildTeacherIds = data.getExtras().getString("id");
            mTxtSubordinateMember.setText(data.getExtras().getString("name"));
        }
    }
}

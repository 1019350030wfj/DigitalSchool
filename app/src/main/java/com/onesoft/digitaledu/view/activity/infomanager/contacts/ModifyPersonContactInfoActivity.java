package com.onesoft.digitaledu.view.activity.infomanager.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.presenter.infomanager.contacts.ModifyContactPersonInfoPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.IModifyPersonInfoView;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改个人
 * Created by Jayden on 2016/11/23.
 */

public class ModifyPersonContactInfoActivity extends ToolBarActivity<ModifyContactPersonInfoPresenter> implements IModifyPersonInfoView {

    public static final int MODIFY_NAME = 0X111;
    public static final int MODIFY_TEL = 0X112;
    public static final int MODIFY_TEL_TYPE = 0X113;
    public static final int MODIFY_REMARK = 0X114;

    public static void startModify(Context context, int requestCode, String cid, String content) {
        Intent intent = new Intent(context, ModifyPersonContactInfoActivity.class);
        intent.putExtra("data", content);
        intent.putExtra("cid", cid);
        intent.putExtra("requestCode", requestCode);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    private View mRlSelect;
    private View mRlEdit;
    private ImageView mImgDel;
    private TextView txt_type;
    private EditText mTxtModify;
    private String mModifyContent;
    private String mCid;
    private int mRequestCode;

    protected List<SingleSelectBean> mSingleSelectBeen;

    private void getDataFromForward() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mRequestCode = bundle.getInt("requestCode");
                mModifyContent = bundle.getString("data");
                mCid = bundle.getString("cid");
            }
        }
    }


    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new ModifyContactPersonInfoPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_modify_contact_person_info, null);
    }

    @Override
    public void initView() {
        mImgDel = (ImageView) findViewById(R.id.img_search_del);
        mTxtModify = (EditText) findViewById(R.id.edt_search);
        txt_type = (TextView) findViewById(R.id.txt_type);
        mRlEdit = findViewById(R.id.rl_edit);
        mRlSelect = findViewById(R.id.rl_select);
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
            public void onClick(View v) {//提交
                if (MODIFY_TEL_TYPE == mRequestCode){//号码类型
                    mPresenter.editInfo(mRequestCode, mModifyContent, mCid);
                } else {
                    mPresenter.editInfo(mRequestCode, mTxtModify.getText().toString().trim(), mCid);
                }
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void initListener() {
        mImgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxtModify.setText("");
            }
        });
        findViewById(R.id.img_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(ModifyPersonContactInfoActivity.this) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(ModifyPersonContactInfoActivity.this){
                            @Override
                            public void bindView(TextView textView,int pos) {
                                textView.setText(mSingleSelectBeen.get(pos).name);
                            }
                        };
                    }
                };
                dialog.show();
                dialog.setDatas(mSingleSelectBeen);
                dialog.setDialogListener(new SingleSelectDialog.IDialogClick<SingleSelectBean>() {
                    @Override
                    public void onConfirm(SingleSelectBean bean,int pos) {
                        txt_type.setText(bean.name);
                        mModifyContent = bean.id;
                    }
                });
            }
        });
    }

    @Override
    public void initData() {
        switch (mRequestCode) {
            case MODIFY_NAME: {
                setTitle(getString(R.string.name));
                break;
            }
            case MODIFY_TEL: {
                setTitle(getString(R.string.no));
                break;
            }
            case MODIFY_TEL_TYPE: {
                setTitle(getString(R.string.number_type));
                mRlEdit.setVisibility(View.GONE);
                mRlSelect.setVisibility(View.VISIBLE);
                if ("1".equals(mModifyContent)){
                    txt_type.setText("手机");
                } else {
                    txt_type.setText("固定电话");
                }
                break;
            }
            case MODIFY_REMARK: {
                setTitle(getString(R.string.remark_info));
                break;
            }
        }
        mTxtModify.setText(mModifyContent);
        mTxtModify.setSelection(mModifyContent.length());
        mSingleSelectBeen = new ArrayList<>();
        mSingleSelectBeen.add(new SingleSelectBean("手机","1"));
        mSingleSelectBeen.add(new SingleSelectBean("固定电话","2"));
        mPageStateLayout.onSucceed();
    }

    @Override
    public void onSuccess(String data) {
        Intent intent = new Intent();
        intent.putExtra("data", data);
        setResult(RESULT_OK, intent);
        finish();
    }
}

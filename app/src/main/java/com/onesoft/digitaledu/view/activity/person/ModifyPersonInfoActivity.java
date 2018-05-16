package com.onesoft.digitaledu.view.activity.person;

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
import com.onesoft.digitaledu.presenter.person.MpdifyPersonInfoPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.person.IModifyPersonInfoView;

/**
 * Created by Jayden on 2016/11/23.
 */

public class ModifyPersonInfoActivity extends ToolBarActivity<MpdifyPersonInfoPresenter> implements IModifyPersonInfoView {

    public static final int MODIFY_EMAIL = 0X111;
    public static final int MODIFY_MOTTO = 0X112;
    public static final int MODIFY_HOBBY = 0X113;
    public static final int MODIFY_SPECIALTY = 0X114;

    public static void startModify(Context context, int requestCode, String content) {
        Intent intent = new Intent(context, ModifyPersonInfoActivity.class);
        intent.putExtra("data", content);
        intent.putExtra("requestCode", requestCode);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    private String mModifyContent;
    private int mRequestCode;

    private void getDataFromForward() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mRequestCode = bundle.getInt("requestCode");
                mModifyContent = bundle.getString("data");
            }
        }
    }

    private ImageView mImgDel;
    private EditText mTxtModify;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new MpdifyPersonInfoPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_modify_person_info, null);
    }

    @Override
    public void initView() {
        mImgDel = (ImageView) findViewById(R.id.img_search_del);
        mTxtModify = (EditText) findViewById(R.id.edt_search);
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
            public void onClick(View v) {
                //提交
                mPresenter.editInfo(mRequestCode,mTxtModify.getText().toString().trim());
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
    }

    @Override
    public void initData() {
        switch (mRequestCode) {
            case MODIFY_EMAIL: {
                setTitle(getString(R.string.email));
                break;
            }
            case MODIFY_MOTTO: {
                setTitle(getString(R.string.motto));
                break;
            }
            case MODIFY_HOBBY: {
                setTitle(getString(R.string.hobby));
                break;
            }
            case MODIFY_SPECIALTY: {
                setTitle(getString(R.string.specialty));
                break;
            }
        }
        mTxtModify.setText(mModifyContent);
        mTxtModify.setSelection(mModifyContent.length());
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

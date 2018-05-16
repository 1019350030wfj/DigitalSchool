package com.onesoft.digitaledu.view.activity.publicfoundation.menu;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.KeyValueBean;
import com.onesoft.digitaledu.model.OperationBtnItem;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.menu.OperatorBtnEditPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.menu.IOperatorBtnEditView;
import com.onesoft.digitaledu.widget.dialog.OperatorBtnDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_TO_MAIN;

/**
 * Created by Jayden on 2016/11/23.
 */
public class OperatorBtnEditActivity extends ToolBarActivity<OperatorBtnEditPresenter> implements IOperatorBtnEditView {

    private ThirdToMainBean mKeyValueBean;
    private TopBtn mTopBtn;

    public static void startOperator(Context context, ThirdToMainBean keyValueBean, TopBtn topBtn) {
        Intent intent = new Intent(context, OperatorBtnEditActivity.class);
        intent.putExtra("keyvalue", keyValueBean);
        intent.putExtra("topbtn", topBtn);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mKeyValueBean = (ThirdToMainBean) getIntent().getExtras().getSerializable("keyvalue");
            mTopBtn = (TopBtn) getIntent().getExtras().getSerializable("topbtn");
        }
    }

    private EditText mEditMenuName;
    private EditText mEditContent;
    private TextView mTxtOperator;
    private View mRlOperator;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new OperatorBtnEditPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_operator_btn_edit, null);
    }

    private ArrayList<String> mOperatorBtn;
    private String mItemBtn;

    @Override
    public void initData() {
        if (mKeyValueBean != null) {
            for (KeyValueBean bean : mKeyValueBean.mShownList) {
                if ("菜单名称".equals(bean.key)) {
                    mEditMenuName.setText(bean.value.toString());
                } else if ("菜单按钮".equals(bean.key)) {
                    mTxtOperator.setText(bean.value.toString());
                }
            }
            mItemBtn = mKeyValueBean.item_btn;
            mOperatorBtn = new ArrayList(Arrays.asList(mKeyValueBean.item_btn.split(",")));
            mEditContent.setText(mKeyValueBean.btn_remark);
        }

        mPageStateLayout.onSucceed();
        if (!TextUtils.isEmpty(mTopBtn.notice)) {//判断是否需要提醒
            RemindUtils.showDialog(this, mTopBtn.notice);
        }
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.operation_btn_edit));

        mEditMenuName = (EditText) findViewById(R.id.edit_menu_name);
        mEditContent = (EditText) findViewById(R.id.edit_content);
        mRlOperator = findViewById(R.id.rl_operation_btn);
        mTxtOperator = (TextView) findViewById(R.id.txt_operation_btn);
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
                mPresenter.modify(mKeyValueBean, mTopBtn, mItemBtn, mEditContent.getText().toString().trim());
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private List<OperationBtnItem> mOperationBtnItems;

    @Override
    public void initListener() {
        mRlOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOperationBtnItems == null) {
                    mPresenter.getSelectBtn();
                } else {
                    showDialog();
                }
            }
        });
    }

    private void showDialog() {
        OperatorBtnDialog dialog = new OperatorBtnDialog(OperatorBtnEditActivity.this);
        dialog.show();
        dialog.setSelectItems(mOperatorBtn);
        dialog.setDatas(mOperationBtnItems);
        dialog.setDialogListener(new OperatorBtnDialog.IDialogClick() {
            @Override
            public void onConfirm() {//确定的话，要改变按钮操作
                StringBuilder builder = new StringBuilder();
                StringBuilder idBuilder = new StringBuilder();
                for (OperationBtnItem item : mOperationBtnItems) {
                    if (mOperatorBtn.contains(item.id)) {
                        builder.append(item.name).append("|");
                        idBuilder.append(item.id).append(",");
                    }
                }
                if (builder.length() > 0) {
                    mTxtOperator.setText(builder.deleteCharAt(builder.length() - 1));
                    mItemBtn = idBuilder.deleteCharAt(idBuilder.length() - 1).toString();
                } else {
                    mTxtOperator.setText("");
                    mItemBtn = "";
                }
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    public void onSuccess(List<OperationBtnItem> operationBtnItems) {
        if (operationBtnItems != null && operationBtnItems.size() > 0) {
            mOperationBtnItems = operationBtnItems;
            showDialog();
        }
    }

    @Override
    public void onEditSuccess() {
        EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_TO_MAIN, ""));//刷新动态列表
        finish();
    }
}

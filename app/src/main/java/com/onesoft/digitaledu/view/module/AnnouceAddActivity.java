package com.onesoft.digitaledu.view.module;

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
import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.Template32Presenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_TO_MAIN;

/**
 * 公告添加
 * Created by Jayden on 2016/11/23.
 */
public class AnnouceAddActivity extends ToolBarActivity<Template32Presenter> implements IAddView {

    private ThirdToMainBean mKeyValueBean;
    private TopBtn mTopBtn;

    private EditText mEditMenuName;
    private EditText mEditContent;
    private TextView mTxtOperator;
    private View mRlOperator;

    private List<SingleSelectBean> mSingleSelectBeen;
    private String mSelectType = "1";

    public static void startAnnouceAdd(Context context, ThirdToMainBean keyValueBean, TopBtn topBtn) {
        Intent intent = new Intent(context, AnnouceAddActivity.class);
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

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new Template32Presenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_add_annouce, null);
    }

    @Override
    public void initData() {
        setTitle(mTopBtn.template_name);
        mSingleSelectBeen = new ArrayList<>();
        mSingleSelectBeen.add(new SingleSelectBean(getString(R.string.all),"1"));
        mSingleSelectBeen.add(new SingleSelectBean(getString(R.string.teacher),"2"));
        mSingleSelectBeen.add(new SingleSelectBean(getString(R.string.student),"3"));
        mPageStateLayout.onSucceed();
        if (!TextUtils.isEmpty(mTopBtn.notice)){//判断是否需要提醒
            RemindUtils.showDialog(this,mTopBtn.notice);
        }
    }

    @Override
    public void initView() {
        mEditMenuName = (EditText) findViewById(R.id.edit_menu_name);
        mEditContent = (EditText) findViewById(R.id.edit_announced_content);
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
            public void onClick(View v) {//提交
                mPresenter.add(mTopBtn, mEditMenuName.getText().toString().trim(),mSelectType,
                        mEditContent.getText().toString().trim());
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void initListener() {
        mRlOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(AnnouceAddActivity.this) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(AnnouceAddActivity.this){
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
                        mTxtOperator.setText(bean.name);
                        mSelectType = bean.id;
                    }
                });
            }
        });
    }

    @Override
    public void onAddSuccess() {
        EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_TO_MAIN, ""));//刷新动态列表
        finish();
    }
}

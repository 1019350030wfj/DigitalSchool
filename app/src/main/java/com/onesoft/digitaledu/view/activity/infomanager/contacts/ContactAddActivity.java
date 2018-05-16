package com.onesoft.digitaledu.view.activity.infomanager.contacts;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.presenter.infomanager.contacts.ContactAddPresenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;
import com.onesoft.digitaledu.widget.wheel.CustomDatePicker;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 添加记录 （通讯录）
 * type 1-手机，2-固定电话
 * Created by Jayden on 2016/11/23.
 */
public class ContactAddActivity extends ToolBarActivity<ContactAddPresenter> implements IAddView {

    private EditText mEditName;
    private EditText mEditPhone;
    private TextView mEditTime;
    private EditText mEditContent;
    private ImageView mImageType;
    private TextView mTxtType;

    private String mSelectMessageType = "1";//默认手机

    protected List<SingleSelectBean> mSingleSelectBeen;
    protected CustomDatePicker customDatePicker;

    @Override
    protected void initPresenter() {
        mPresenter = new ContactAddPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_contact_add, null);
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.contact));
        mPageStateLayout.onSucceed();

        mImageType = (ImageView) findViewById(R.id.img_type);
        mTxtType = (TextView) findViewById(R.id.txt_type);
        mEditName = (EditText) findViewById(R.id.edit_name);
        mEditTime = (TextView) findViewById(R.id.edit_add_time);
        mEditPhone = (EditText) findViewById(R.id.edit_no);
        mEditContent = (EditText) findViewById(R.id.edit_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpaper_setting, menu);
        return true;
    }

    @Override
    public void initData() {
        mSingleSelectBeen = new ArrayList<>();
        mSingleSelectBeen.add(new SingleSelectBean("手机","1"));
        mSingleSelectBeen.add(new SingleSelectBean("固定电话","2"));
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
                mPresenter.sendMessage(mEditName.getText().toString().trim(),
                        mEditPhone.getText().toString().trim(),mEditContent.getText().toString().trim(),mSelectMessageType);
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void initListener() {
        findViewById(R.id.img_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDatePicker == null){
                    customDatePicker = new CustomDatePicker(ContactAddActivity.this, new CustomDatePicker.ResultHandler() {
                        @Override
                        public void handle(String time) { // 回调接口，获得选中的时间
                           mEditTime.setText(time.split(" ")[0]);
                        }
                    }, "1910-01-01 00:00", "2910-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                    customDatePicker.showSpecificTime(false); // 不显示时和分
                    customDatePicker.setIsLoop(true); // 允许循环滚动
                }
                // 日期格式为yyyy-MM-dd
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                customDatePicker.show(sdf.format(new Date()));
            }
        });

        mImageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(ContactAddActivity.this) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(ContactAddActivity.this){
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
                        mTxtType.setText(bean.name);
                        mSelectMessageType = bean.id;
                    }
                });
            }
        });
    }

    @Override
    public void onAddSuccess() {
        EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_PERSON_CONTACT,""));
        finish();
    }
}

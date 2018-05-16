package com.onesoft.digitaledu.view.activity.message;

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
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.message.SendMessagePresenter;
import com.onesoft.digitaledu.utils.MaxLengthWatcher;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.message.ISendMessageView;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;
import com.onesoft.digitaledu.widget.treerecyclerview.activity.TreeActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.onesoft.digitaledu.model.BaseEvent.SEND_MESSAGE;

/**
 * 发送消息
 * 1消息 2通知 3公告
 * Created by Jayden on 2016/11/7.
 */
public class SendMessageActivity extends ToolBarActivity<SendMessagePresenter> implements ISendMessageView {

    public static final int REQUEST_CODE_RECIPIENT = 0x111;
    public static final int FROM_SYSTEM_CONTACT = 0X100;//从系统通讯录过来
    public static final int FROM_SYSTEM_CONTACT_MASS = 0X101;//从系统通讯录过来且是群发
    public static final int FROM_GROUP_CONTACT_MASS = 0X102;//从群组通讯录过来且是群发

    private ImageView mImageType;
    private ImageView mImageRecipient;
    private TextView mTxtType;
    private TextView mTxtRecipient;//teacher1,teacher2,student1,student2)
    private EditText mEditTitle;
    private EditText mEditContent;

    protected List<SingleSelectBean> mSingleSelectBeen;
    private String mSelectMessageType = "1";//默认是消息
    private String touserids = "";
    private String touserNames = "";
    private int mFromWhere;

    public static void startSendMessageActivity(Context context) {
        Intent intent = new Intent(context, SendMessageActivity.class);
        context.startActivity(intent);
    }

    public static void startSendMessageActivity(Context context, int fromWhere, String data, String dataIds) {
        Intent intent = new Intent(context, SendMessageActivity.class);
        intent.putExtra("fromWhere", fromWhere);
        intent.putExtra("data", data);
        intent.putExtra("dataIds", dataIds);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                touserNames = bundle.getString("data");
                touserids = bundle.getString("dataIds");
                mFromWhere = bundle.getInt("fromWhere");
            }
        }
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new SendMessagePresenter(this, this);
    }

    @Override
    public void initData() {
        switch (mFromWhere) {
            case FROM_GROUP_CONTACT_MASS://群组通讯录群发
            case FROM_SYSTEM_CONTACT_MASS: {//系统通讯录群发
                mImageType.setVisibility(View.VISIBLE);
                mImageRecipient.setVisibility(View.INVISIBLE);
                mTxtRecipient.setText(touserNames);
                setTitle(getResources().getString(R.string.send_message_mass));
                break;
            }
            case FROM_SYSTEM_CONTACT:{//系统通讯录
                mImageType.setVisibility(View.INVISIBLE);
                mImageRecipient.setVisibility(View.INVISIBLE);
                mTxtRecipient.setText(touserNames);
                setTitle(getResources().getString(R.string.send_message));
                break;
            }
            default: {//消息中心
                mImageType.setVisibility(View.INVISIBLE);
                mImageRecipient.setVisibility(View.VISIBLE);
                mTxtRecipient.setText(touserNames);
                setTitle(getResources().getString(R.string.send_message));
                break;
            }
        }

        mSingleSelectBeen = new ArrayList<>();
        mSingleSelectBeen.add(new SingleSelectBean("消息", "1"));
        mSingleSelectBeen.add(new SingleSelectBean("通知", "2"));
        mSingleSelectBeen.add(new SingleSelectBean("公告", "3"));
    }

    @Override
    public void initView() {
        mPageStateLayout.onSucceed();
        mImageType = (ImageView) findViewById(R.id.img_type);
        mImageRecipient = (ImageView) findViewById(R.id.img_recipient);
        mTxtType = (TextView) findViewById(R.id.txt_type);
        mTxtRecipient = (TextView) findViewById(R.id.txt_recipient);
        mEditTitle = (EditText) findViewById(R.id.edit_title);
        mEditContent = (EditText) findViewById(R.id.edit_content);

        mEditTitle.addTextChangedListener(new MaxLengthWatcher(12, mEditTitle));
        mEditContent.addTextChangedListener(new MaxLengthWatcher(120, mEditContent));

    }

    @Override
    public void initListener() {
        mImageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(SendMessageActivity.this) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(SendMessageActivity.this) {
                            @Override
                            public void bindView(TextView textView, int pos) {
                                textView.setText(mSingleSelectBeen.get(pos).name);
                            }
                        };
                    }
                };
                dialog.show();
                dialog.setDatas(mSingleSelectBeen);
                dialog.setDialogListener(new SingleSelectDialog.IDialogClick<SingleSelectBean>() {
                    @Override
                    public void onConfirm(SingleSelectBean bean, int pos) {
                        mSelectMessageType = bean.id;
                        mTxtType.setText(bean.name);
                    }
                });
            }
        });

        mImageRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreeActivity.startRecipientActivity(SendMessageActivity.this, REQUEST_CODE_RECIPIENT);
            }
        });
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
        textView.setText(getString(R.string.send));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null) { //发送
                    switch (mFromWhere) {
                        case FROM_GROUP_CONTACT_MASS: {
                            mPresenter.sendMessage(HttpUrl.SEND_MESSAGE_GROUP, touserids, mEditTitle.getText().toString().trim(),
                                    mEditContent.getText().toString().trim(), mSelectMessageType);
                            break;
                        }
                        case FROM_SYSTEM_CONTACT_MASS: {
                            mPresenter.sendMessage(HttpUrl.SEND_MESSAGE_DEPART, touserids, mEditTitle.getText().toString().trim(),
                                    mEditContent.getText().toString().trim(), mSelectMessageType);
                            break;
                        }
                        default: {
                            mPresenter.sendMessage(HttpUrl.SEND_MESSAGE, touserids, mEditTitle.getText().toString().trim(),
                                    mEditContent.getText().toString().trim(), mSelectMessageType);
                        }
                    }

                }
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_send_message, null);
    }

    @Override
    public void onSuccess() {
        EventBus.getDefault().post(new BaseEvent(SEND_MESSAGE, ""));//刷新发件箱列表
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_RECIPIENT && data != null) {
            touserids = data.getExtras().getString("id");
            mTxtRecipient.setText(data.getExtras().getString("name"));
        }
    }
}

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
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.presenter.message.ReplyMessagePresenter;
import com.onesoft.digitaledu.utils.MaxLengthWatcher;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.message.ISendMessageView;

import org.greenrobot.eventbus.EventBus;

import static com.onesoft.digitaledu.model.BaseEvent.SEND_MESSAGE;

/**
 * 消息回复
 * Created by Jayden on 2016/11/7.
 */
public class ReplyMessageActivity extends ToolBarActivity<ReplyMessagePresenter> implements ISendMessageView {

    public static final int REQUEST_CODE_RECIPIENT = 0x111;

    private ImageView mImageType;
    private TextView mTxtRecipient;//teacher1,teacher2,student1,student2)
    private EditText mEditTitle;
    private EditText mEditContent;

    private BoxBean mBoxBean;

    private void getDataFromForward() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mBoxBean = (BoxBean) bundle.getSerializable("boxBean");
        }
    }

    public static void startReply(Context context, BoxBean boxBean) {
        Intent intent = new Intent(context, ReplyMessageActivity.class);
        intent.putExtra("boxBean", boxBean);
        context.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new ReplyMessagePresenter(this, this);
    }

    @Override
    public void initData() {
        setTitle(getResources().getString(R.string.reply_msg));
        mTxtRecipient.setText(mBoxBean.name);
        mEditTitle.setText("Re:"+mBoxBean.title);
    }

    @Override
    public void initView() {
        mPageStateLayout.onSucceed();
        mImageType = (ImageView) findViewById(R.id.img_type);
        mImageType.setVisibility(View.INVISIBLE);
        mTxtRecipient = (TextView) findViewById(R.id.txt_recipient);
        mEditTitle = (EditText) findViewById(R.id.edit_title);
        mEditTitle.setFocusable(false);
        mEditContent = (EditText) findViewById(R.id.edit_content);

        mEditTitle.addTextChangedListener(new MaxLengthWatcher(12, mEditTitle));
        mEditContent.addTextChangedListener(new MaxLengthWatcher(120, mEditContent));
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
                    mPresenter.sendMessage(mBoxBean.fromuser,mBoxBean.fromusertype, mEditTitle.getText().toString().trim(),
                            mEditContent.getText().toString().trim());
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
        return inflater.inflate(R.layout.activity_reply_message, null);
    }

    @Override
    public void onSuccess() {
        EventBus.getDefault().post(new BaseEvent(SEND_MESSAGE, ""));//刷新发件箱列表
        finish();
    }
}

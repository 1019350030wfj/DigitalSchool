package com.onesoft.digitaledu.view.activity.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.presenter.message.MessageDetailPresenter;
import com.onesoft.digitaledu.utils.MaxLengthWatcher;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.message.IMessageDetailView;
import com.onesoft.digitaledu.widget.dialog.RecipientDialog;

/**
 * 信息详情
 * Created by Jayden on 2016/11/7.
 */
public class MessageDetailActivity extends ToolBarActivity<MessageDetailPresenter> implements IMessageDetailView {

    private BoxBean mBoxBean;

    public static void startMessageDetail(Context context, BoxBean boxBean) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra("boxBean", boxBean);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mBoxBean = (BoxBean) bundle.getSerializable("boxBean");
        }
    }

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_NOTICE = 1;

    private ImageView mImageType;
    private TextView mTxtTitle;
    private TextView mTxtSendTime;
    private TextView mTxtSendPerson;
    private TextView mTxtRecipient;
    private EditText mEditContent;

    private int mSelectMessageType = TYPE_MESSAGE;

    public static void startSendMessageActivity(Context context) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new MessageDetailPresenter(this, this);
    }

    @Override
    public void initData() {
        setTitle(getResources().getString(R.string.message_detail));
    }

    @Override
    public void initView() {
        mPageStateLayout.onSucceed();
        mImageType = (ImageView) findViewById(R.id.img_type);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mTxtRecipient = (TextView) findViewById(R.id.txt_recipient);
        mTxtSendPerson = (TextView) findViewById(R.id.txt_send_person);
        mTxtSendTime = (TextView) findViewById(R.id.txt_send_time);
        mEditContent = (EditText) findViewById(R.id.edit_title);

        mEditContent.addTextChangedListener(new MaxLengthWatcher(120, mEditContent));
    }

    @Override
    public void initListener() {
        mImageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipientDialog dialog = new RecipientDialog(MessageDetailActivity.this);
                dialog.show();
            }
        });
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_message_detail, null);
    }
}

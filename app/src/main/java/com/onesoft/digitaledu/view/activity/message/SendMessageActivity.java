package com.onesoft.digitaledu.view.activity.message;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.presenter.message.SendMessagePresenter;
import com.onesoft.digitaledu.utils.MaxLengthWatcher;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.message.ISendMessageView;
import com.onesoft.digitaledu.widget.dialog.SelectMessageDialog;
import com.onesoft.digitaledu.widget.treerecyclerview.activity.TreeActivity;

import static com.onesoft.digitaledu.widget.dialog.SelectMessageDialog.TYPE_MESSAGE;
import static com.onesoft.digitaledu.widget.dialog.SelectMessageDialog.TYPE_NOTICE;

/**
 * Created by Jayden on 2016/11/7.
 */
public class SendMessageActivity extends ToolBarActivity<SendMessagePresenter> implements ISendMessageView {


    public static final int REQUEST_CODE_RECIPIENT = 0x111;

    private ImageView mImageType;
    private ImageView mImageRecipient;
    private TextView mTxtType;
    private EditText mEditTitle;
    private EditText mEditContent;

    private int mSelectMessageType = TYPE_MESSAGE;

    public static void startSendMessageActivity(Context context) {
        Intent intent = new Intent(context, SendMessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new SendMessagePresenter(this, this);
    }

    @Override
    public void initData() {
        setTitle(getResources().getString(R.string.send_message));
    }

    @Override
    public void initView() {
        mPageStateLayout.onSucceed();
        mImageType = (ImageView) findViewById(R.id.img_type);
        mImageRecipient = (ImageView) findViewById(R.id.img_recipient);
        mTxtType = (TextView) findViewById(R.id.txt_type);
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
                SelectMessageDialog dialog = new SelectMessageDialog(SendMessageActivity.this);
                dialog.show();
                dialog.setSelect(mSelectMessageType);
                dialog.setSelectTypeListener(new SelectMessageDialog.ISelectMsgType() {
                    @Override
                    public void onMessage() {
                        mSelectMessageType = TYPE_MESSAGE;
                        mTxtType.setText(getString(R.string.message));
                    }

                    @Override
                    public void onNotice() {
                        mSelectMessageType = TYPE_NOTICE;
                        mTxtType.setText(getString(R.string.notice));
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
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_send_message, null);
    }
}

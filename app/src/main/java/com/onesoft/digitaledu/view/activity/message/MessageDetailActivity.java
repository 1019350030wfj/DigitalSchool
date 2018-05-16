package com.onesoft.digitaledu.view.activity.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.BoxBean;
import com.onesoft.digitaledu.model.BoxDetail;
import com.onesoft.digitaledu.presenter.message.DeleteMessagePresenter;
import com.onesoft.digitaledu.presenter.message.MessageDetailPresenter;
import com.onesoft.digitaledu.utils.MaxLengthWatcher;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.message.IDelMesView;
import com.onesoft.digitaledu.view.iview.message.IMessageDetailView;
import com.onesoft.digitaledu.widget.dialog.RecipientDialog;

import org.greenrobot.eventbus.EventBus;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_IN_BOX;

/**
 * 信息详情  收件箱
 * Created by Jayden on 2016/11/7.
 */
public class MessageDetailActivity extends ToolBarActivity<MessageDetailPresenter> implements IDelMesView, IMessageDetailView {

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

    private ImageView mImageType;
    private TextView mTxtTitle;
    private TextView mTxtSendTime;
    private TextView mTxtSendPerson;
    private TextView mTxtRecipient;
    private EditText mEditContent;

    private DeleteMessagePresenter mDeleteMessagePresenter;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new MessageDetailPresenter(this, this);
        mDeleteMessagePresenter = new DeleteMessagePresenter(this, this);
    }

    @Override
    public void initData() {
        setTitle(getResources().getString(R.string.message_detail));
        mPresenter.getMessageDetail(mBoxBean.id);
    }

    @Override
    public void initView() {
        mImageType = (ImageView) findViewById(R.id.img_type);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mTxtRecipient = (TextView) findViewById(R.id.txt_recipient);
        mTxtSendPerson = (TextView) findViewById(R.id.txt_send_person);
        mTxtSendTime = (TextView) findViewById(R.id.txt_send_time);
        mEditContent = (EditText) findViewById(R.id.edit_title);
        mEditContent.setFocusable(false);
        mEditContent.addTextChangedListener(new MaxLengthWatcher(120, mEditContent));
    }

    @Override
    public void initListener() {
        mImageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipientDialog dialog = new RecipientDialog(MessageDetailActivity.this);
                dialog.show();
                dialog.setData(mBoxBean.imgUrl,SPHelper.getUserName(MessageDetailActivity.this),
                        SPHelper.getUserId(MessageDetailActivity.this));
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteMessagePresenter.delInBoxMsg(mBoxBean.id);
            }
        });
        findViewById(R.id.btn_reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(mBoxBean.typeid)) {//消息，0是通知
                    ReplyMessageActivity.startReply(MessageDetailActivity.this, mBoxBean);
                }
            }
        });
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_message_detail, null);
    }

    @Override
    public void onSuccess(BoxDetail detail) {
        mPageStateLayout.onSucceed();
        mEditContent.setText(Html.fromHtml(detail.content));
        mTxtTitle.setText(detail.title);
        mTxtSendTime.setText(detail.time);
        mTxtSendPerson.setText(detail.name);//发件人
        mTxtRecipient.setText(SPHelper.getUserName(this));//收件人
    }


    @Override
    public void onDelSuccess() { //删除消息成功
        EventBus.getDefault().post(new BaseEvent(UPDATE_IN_BOX, ""));//刷新收件箱列表
        finish();
    }
}

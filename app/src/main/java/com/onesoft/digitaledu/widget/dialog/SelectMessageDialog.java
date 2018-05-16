package com.onesoft.digitaledu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.onesoft.digitaledu.R;

/**
 * Created by Jayden on 2016/11/7.
 */

public class SelectMessageDialog extends Dialog {

    public static final int TYPE_MESSAGE = 1;//消息
    public static final int TYPE_NOTICE = 0;//通知

    private TextView mTxtTitle;
    private TextView mTxtNotice;
    private TextView mTxtMessage;

    public SelectMessageDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_message);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mTxtNotice = (TextView) findViewById(R.id.txt_notice);
        mTxtMessage = (TextView) findViewById(R.id.txt_msg);

        mTxtNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onNotice();
                }
            }
        });
        mTxtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onMessage();
                }
            }
        });
    }

    public void setSelect(int type) {
        if (type == TYPE_NOTICE) {
            mTxtNotice.setBackgroundColor(getContext().getResources().getColor(R.color.color_fafafa));
            mTxtMessage.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        } else if (type == TYPE_MESSAGE) {
            mTxtMessage.setBackgroundColor(getContext().getResources().getColor(R.color.color_fafafa));
            mTxtNotice.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        }
    }

    private ISelectMsgType mListener;

    public void setSelectTypeListener(ISelectMsgType iSelectMsgType) {
        this.mListener = iSelectMsgType;
    }

    public void setTitleText(String title) {
        mTxtTitle.setText(title);
    }

    public void setText(String string, String string1) {
        mTxtNotice.setText(string);
        mTxtMessage.setText(string1);
    }

    public interface ISelectMsgType {
        void onMessage();

        void onNotice();
    }
}

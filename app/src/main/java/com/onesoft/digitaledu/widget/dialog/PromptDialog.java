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

public class PromptDialog extends Dialog {

    private TextView mTxtTitle;
    private TextView mTxtNotice;
    private TextView mTxtMessage;

    public PromptDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mTxtNotice = (TextView) findViewById(R.id.txt_prompt);
        mTxtMessage = (TextView) findViewById(R.id.txt_confirm);

        mTxtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onConfirm();
                }
            }
        });
    }

    private ISelectMsgType mListener;

    public void setSelectTypeListener(ISelectMsgType iSelectMsgType) {
        this.mListener = iSelectMsgType;
    }

    public void setTitleText(String title) {
        mTxtTitle.setText(title);
    }

    public void setText(String string) {
        mTxtNotice.setText(string);
    }

    public interface ISelectMsgType {
        void onConfirm();
    }
}

package com.onesoft.digitaledu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onesoft.digitaledu.R;

/**
 * 新增群组
 * Created by Jayden on 2016/11/7.
 */

public class AddGroupDialog extends Dialog {

    private TextView mTxtTitle;
    private Button mTxtNotice;
    private Button mTxtMessage;
    private EditText mEditText;

    public AddGroupDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_group);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mTxtNotice = (Button) findViewById(R.id.negativeButton);
        mTxtMessage = (Button) findViewById(R.id.positiveButton);
        mEditText = (EditText) findViewById(R.id.edt_group_name);

        mTxtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onConfirm(mEditText.getText().toString().trim());
                }
            }
        });

        mTxtNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private ISelectMsgType mListener;

    public void setListener(ISelectMsgType iSelectMsgType) {
        this.mListener = iSelectMsgType;
    }

    public void setTitleText(String title) {
        mTxtTitle.setText(title);
    }

    public void setText(String string) {
        mTxtNotice.setText(string);
    }

    public interface ISelectMsgType {
        void onConfirm(String content);
    }
}

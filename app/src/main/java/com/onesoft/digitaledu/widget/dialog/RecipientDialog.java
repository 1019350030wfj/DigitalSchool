package com.onesoft.digitaledu.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.netlibrary.utils.ImageHandler;

/**
 * 收件人
 * Created by Jayden on 2016/11/7.
 */

public class RecipientDialog extends Dialog {

    private TextView mTxtName;
    private TextView mTxtNumber;
    private ImageView mImageAvatar;
    private Context mContext;

    public RecipientDialog(Context context) {
        super(context, R.style.dialog_transparent);
        mContext = context;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recipient);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mTxtName = (TextView) findViewById(R.id.name);
        mTxtNumber = (TextView) findViewById(R.id.number);
        mImageAvatar = (ImageView) findViewById(R.id.avatar);
    }

    public void setData(String imgUrl,String name,String number) {
        ImageHandler.getAvater((Activity) mContext,mImageAvatar,imgUrl);
        mTxtName.setText(name);
        mTxtNumber.setText(number);
    }
}

package com.onesoft.digitaledu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;

/**
 * 加载弹框
 * Created by Jayden on 2016/11/7.
 */

public class LoadingDialog extends Dialog {

    private TextView mTxtMessage;
    private ImageView mImageLoading;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mTxtMessage = (TextView) findViewById(R.id.txt_prompt);
        mImageLoading = (ImageView) findViewById(R.id.loading);

        // 刷新中动画
        RotateAnimation mHeaderAnimation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mHeaderAnimation.setInterpolator(new LinearInterpolator());
        mHeaderAnimation.setDuration(1200);
        mHeaderAnimation.setRepeatCount(Animation.INFINITE);
        mHeaderAnimation.setRepeatMode(Animation.RESTART);

        mImageLoading.clearAnimation();
        mImageLoading.startAnimation(mHeaderAnimation);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mImageLoading.clearAnimation();
            }
        });

    }
}

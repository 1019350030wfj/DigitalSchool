package com.onesoft.digitaledu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.onesoft.digitaledu.R;

/**
 * 添加附近弹出框
 * Created by Jayden on 2016/12/3.
 */
public class AddAttachmentDialog extends Dialog {

    private View mRlAlbum;
    private View mRlShot;
    private View mRlFile;
    private View mCancel;

    public AddAttachmentDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_attachment);
        final WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(mlp);
        getWindow().setWindowAnimations(R.style.dialog_bottom_enter);  //添加动画

        mRlAlbum = findViewById(R.id.rl_album);
        mRlShot = findViewById(R.id.rl_shot);
        mRlFile = findViewById(R.id.rl_file);
        mCancel = findViewById(R.id.cancel);

        mRlAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onClickAlbum();
                }
            }
        });
        mRlShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onClickShot();
                }
            }
        });
        mRlFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onClickFile();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private IAttachListener mListener;

    public void setListener(IAttachListener listener) {
        mListener = listener;
    }

    public interface IAttachListener {
        void onClickAlbum();

        void onClickShot();

        void onClickFile();
    }
}

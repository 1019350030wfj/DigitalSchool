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
 * 文件下载
 * Created by Jayden on 2016/11/7.
 */

public class FileDownloadDialog extends Dialog {

    private TextView mTxtFileTitle;
    private TextView mTxtFileName;
    private TextView mTxtFileSize;
    private TextView mTxtConfirm;

    public FileDownloadDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_file_download);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mTxtFileTitle = (TextView) findViewById(R.id.txt_title);
        mTxtFileName = (TextView) findViewById(R.id.txt_file_name);
        mTxtFileSize = (TextView) findViewById(R.id.txt_file_size);
        mTxtConfirm = (TextView) findViewById(R.id.txt_msg);

        mTxtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onConfirm();
                }
            }
        });
    }

    public void setTextHint(String content) {
        mTxtFileName.setVisibility(View.GONE);
        mTxtFileSize.setVisibility(View.GONE);
        mTxtFileTitle.setText(content);
    }

    private IFileDownload mListener;

    public void setFileDownloadListener(IFileDownload iSelectMsgType) {
        this.mListener = iSelectMsgType;
    }

    public interface IFileDownload {
        void onConfirm();
    }
}

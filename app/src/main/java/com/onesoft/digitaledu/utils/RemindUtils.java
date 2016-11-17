package com.onesoft.digitaledu.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.onesoft.digitaledu.widget.dialog.CustomDialog;


/**
 * Created by Jayden on 2016/7/26.
 */
public class RemindUtils {

    private static CustomDialog mDialog;

    public static void showDialog(Context context, String hint) {
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        CustomDialog.Builder builder = new CustomDialog.Builder(
                context);
        builder.setTitle("系统提示");
        builder.setMessage(hint);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置你的操作事项
            }
        });
        mDialog = builder.create();
        mDialog.show();
    }
}

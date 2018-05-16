package com.onesoft.digitaledu.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.widget.dialog.CustomDialog;
import com.onesoft.digitaledu.widget.dialog.LoadingProgressDialog;


/**
 * Created by Jayden on 2016/7/26.
 */
public class RemindUtils {

    private static Dialog mDialog;

    public static void showDialog(Context context, String hint) {
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        showDialog(context, hint, null);
    }

    public static void showDialogWithNag(Context context, String hint, final DialogInterface.OnClickListener listener) {
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        CustomDialog.Builder builder = new CustomDialog.Builder(
                context);
        builder.setTitle("提示");
        builder.setMessage(hint);
        builder.setPositiveButton(context.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(dialog, which);
                }
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog = builder.create();
        mDialog.show();
    }

    public static void showDialog(Context context, String hint, final DialogInterface.OnClickListener listener) {
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        CustomDialog.Builder builder = new CustomDialog.Builder(
                context);
        builder.setTitle("提示");
        builder.setMessage(hint);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(dialog, which);
                }
            }
        });
        mDialog = builder.create();
        mDialog.show();
    }

    public static void showLoading(Context context) {
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        mDialog = new LoadingProgressDialog(context);
        mDialog.show();
    }

    public static void hideLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}

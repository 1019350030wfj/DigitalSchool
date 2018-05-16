package com.onesoft.digitaledu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.yancy.gallerypick.utils.AppUtils;

import java.util.List;

/**
 * 多选
 * Created by Jayden on 2016/11/24.
 */

public abstract class MoreSelectDialog<T> extends Dialog{

    private ListView mListView;
    private TextView mTextTitle;
    private Button negativeButton;
    private Button positiveButton;
    private BaseAbsListAdapter<T> mAdapter;

    private int mItemHeight;

    public MoreSelectDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mItemHeight = (int) AppUtils.dipToPx(context, 48.0f);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_operation_btn);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mListView = (ListView) findViewById(R.id.listview);
        mTextTitle = (TextView) findViewById(R.id.title);
        negativeButton = (Button) findViewById(R.id.negativeButton);
        positiveButton = (Button) findViewById(R.id.positiveButton);

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onCancel();
                }
            }
        });

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onConfirm();
                }
            }
        });

        mAdapter = getAdapter();
        mListView.setAdapter(mAdapter);
    }

    public abstract BaseAbsListAdapter getAdapter();

    private IDialogClick mListener;

    public void setDialogListener(IDialogClick iSelectMsgType) {
        this.mListener = iSelectMsgType;
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
    }

    public void setDatas(List<T> items) {
        if (items == null || items.size() <= 0) {
            return;
        }
        mAdapter.setDatas(items);
        if (items.size() > 7) {
            ViewGroup.LayoutParams layoutParams = mListView.getLayoutParams();
            layoutParams.height = mItemHeight * 7;
            mListView.setLayoutParams(layoutParams);
        }
    }

    public interface IDialogClick {
        void onConfirm();

        void onCancel();
    }
}


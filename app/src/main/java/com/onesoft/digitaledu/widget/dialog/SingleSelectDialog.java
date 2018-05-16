package com.onesoft.digitaledu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onesoft.digitaledu.R;
import com.yancy.gallerypick.utils.AppUtils;

import java.util.List;

/**
 * 单选项
 * Created by Jayden on 2016/11/24.
 */

public abstract class SingleSelectDialog<T> extends Dialog {

    private ListView mListView;
    private SingleSelectAdapter mAdapter;

    private int mItemHeight;

    public SingleSelectDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        mItemHeight = (int) AppUtils.dipToPx(context, 48.0f);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_single_select);
        final WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onConfirm(mAdapter.getItem(position),position);
                }
                dismiss();
            }
        });

        mAdapter =getAdapter();
        mListView.setAdapter(mAdapter);
    }

    public abstract SingleSelectAdapter getAdapter();

    private IDialogClick mListener;

    public void setDialogListener(IDialogClick iSelectMsgType) {
        this.mListener = iSelectMsgType;
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

    public interface IDialogClick<T> {
        void onConfirm(T bean,int pos);
    }
}


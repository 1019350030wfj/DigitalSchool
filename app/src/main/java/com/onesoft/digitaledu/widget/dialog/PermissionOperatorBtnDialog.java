package com.onesoft.digitaledu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.OperationBtnItem;
import com.yancy.gallerypick.utils.AppUtils;

import java.util.List;

/**
 * 权限-操作按钮管理
 * Created by Jayden on 2016/11/24.
 */

public class PermissionOperatorBtnDialog extends Dialog {

    private ListView mListView;
    private TextView mTextTitle;
    private Button negativeButton;
    private Button positiveButton;
    private OperationBtnAdapter mAdapter;

    private int mItemHeight;

    public PermissionOperatorBtnDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mItemHeight = (int) AppUtils.dipToPx(context, 48.0f);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_operation_btn_permission);
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

        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.item_permission_operation_btn_header, mListView, false);
        mListView.addHeaderView(headerView);
        final CheckBox imageRadio = (CheckBox) headerView.findViewById(R.id.img_radio);
//        imageRadio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageRadio.setChecked(!imageRadio.isChecked());
//                mAdapter.setShow(imageRadio.isChecked());
//            }
//        });
        imageRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imageRadio.setChecked(isChecked);
                mAdapter.setShow(isChecked);
            }
        });

        mAdapter = new OperationBtnAdapter(getContext(), false);
        mListView.setAdapter(mAdapter);
    }

    private IDialogClick mListener;

    public void setDialogListener(IDialogClick iSelectMsgType) {
        this.mListener = iSelectMsgType;
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
    }

    public void setDatas(List<OperationBtnItem> items) {
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


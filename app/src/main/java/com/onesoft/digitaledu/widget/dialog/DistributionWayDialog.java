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
 * Created by Jayden on 2016/11/7.
 */

public class DistributionWayDialog extends Dialog {

    public static final int TYPE_DEPARTMENT = 1;
    public static final int TYPE_COLLEAGE = 2;
    public static final int TYPE_SCHOOL = 3;

    private TextView mTxtTitle;
    private TextView mTxtDepartment;
    private TextView mTxtColleage;
    private TextView mTxtSchool;

    public DistributionWayDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_distribution_way);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mTxtDepartment = (TextView) findViewById(R.id.deparment);
        mTxtColleage = (TextView) findViewById(R.id.colleage);
        mTxtSchool = (TextView) findViewById(R.id.school);

        mTxtSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onItemSelect(TYPE_SCHOOL,getContext().getString(R.string.school));
                }
            }
        });
        mTxtDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onItemSelect(TYPE_DEPARTMENT,getContext().getString(R.string.colleage));
                }
            }
        });
        mTxtColleage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onItemSelect(TYPE_COLLEAGE,getContext().getString(R.string.department));
                }
            }
        });
    }

    public void setSelect(int type) {
        if (type == TYPE_DEPARTMENT) {
            mTxtDepartment.setBackgroundColor(getContext().getResources().getColor(R.color.color_fafafa));
            mTxtColleage.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
            mTxtSchool.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        } else if (type == TYPE_COLLEAGE) {
            mTxtColleage.setBackgroundColor(getContext().getResources().getColor(R.color.color_fafafa));
            mTxtDepartment.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
            mTxtSchool.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        } else if (type == TYPE_SCHOOL) {
            mTxtSchool.setBackgroundColor(getContext().getResources().getColor(R.color.color_fafafa));
            mTxtDepartment.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
            mTxtColleage.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        }
    }

    private ISelectMsgType mListener;

    public void setSelectTypeListener(ISelectMsgType iSelectMsgType) {
        this.mListener = iSelectMsgType;
    }

    public interface ISelectMsgType {
        void onItemSelect(int type,String text);
    }
}

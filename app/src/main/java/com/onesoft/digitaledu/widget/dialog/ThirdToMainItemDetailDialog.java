package com.onesoft.digitaledu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.ThirdToMainDetail;
import com.yancy.gallerypick.utils.AppUtils;

/**
 *
 * Created by Jayden on 2016/11/24.
 */

public class ThirdToMainItemDetailDialog extends Dialog {

    private ScrollView mListView;
    private TextView mTextTitle;
    private TextView mTextDetail;
    private Button positiveButton;

    private int mItemHeight;

    public ThirdToMainItemDetailDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mItemHeight = (int) AppUtils.dipToPx(context, 277.0f);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_third_detail);
        WindowManager.LayoutParams mlp = getWindow().getAttributes();
        mlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(mlp);

        mListView = (ScrollView) findViewById(R.id.scrollView);
        mTextTitle = (TextView) findViewById(R.id.title);
        mTextDetail = (TextView) findViewById(R.id.txt_detail);
        positiveButton = (Button) findViewById(R.id.positiveButton);
        mTextDetail.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mTextDetail.getHeight() > mItemHeight){
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
                    layoutParams.height = mItemHeight;
                    mListView.setLayoutParams(layoutParams);
                    mTextDetail.getViewTreeObserver().removeGlobalOnLayoutListener(this);
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

    }

    private IDialogClick mListener;

    public void setDialogListener(IDialogClick iSelectMsgType) {
        this.mListener = iSelectMsgType;
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
    }

    public void setDatas(ThirdToMainDetail items) {
        mTextDetail.setText(Html.fromHtml(items.detail));
    }

    public interface IDialogClick {
        void onConfirm();
    }
}


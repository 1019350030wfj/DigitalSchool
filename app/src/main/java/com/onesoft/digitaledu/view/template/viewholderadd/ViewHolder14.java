package com.onesoft.digitaledu.view.template.viewholderadd;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;

/**
 * //文字+编辑框(输入标题不超过12个字)
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolder14 extends BaseViewHolder{

    protected TextView mTxtName;
    protected EditText mTxtContent;
    protected boolean isLimitNumber;
    protected int mLimitNumber;

    public ViewHolder14(Context context) {
        mContext = context;
        isLimitNumber = false;
    }

    public View createView(ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_4, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.txt_name);
        mTxtContent = (EditText) view.findViewById(R.id.edit_content);
        return view;
    }

    public void bindView(final TopBtn mTopBtn, final int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        if (TextUtils.isEmpty(template10Bean.showValue)){
            if ("91".equals(mTopBtn.template_id)){
                mTxtContent.setHint(mContext.getString(R.string.hint_title));
                isLimitNumber = true;
                mLimitNumber = 12;
            } else {
                isLimitNumber = false;
                mTxtContent.setHint("请输入" + mTopBtn.mTemplate10Been.get(position).name);
            }
        } else {
            mTxtContent.setText(template10Bean.showValue);
        }
        mTxtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isLimitNumber ){
                    Editable editable = mTxtContent.getText();
                    int len = editable.length();

                    if (len > mLimitNumber) {
                        int selEndIndex = Selection.getSelectionEnd(editable);
                        String str = editable.toString();
                        //截取新字符串
                        String newStr = str.substring(0, mLimitNumber);
                        mTxtContent.setText(newStr);
                        editable = mTxtContent.getText();

                        //新字符串的长度
                        int newLen = editable.length();
                        //旧光标位置超过字符串长度
                        if (selEndIndex > newLen) {
                            selEndIndex = editable.length();
                        }
                        //设置新光标所在的位置
                        Selection.setSelection(editable, selEndIndex);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                template10Bean.showValue = s.toString();
                template10Bean.content = s.toString();
            }

        });
    }
}

package com.onesoft.digitaledu.view.template.viewholderadd;

import android.content.Context;
import android.text.Editable;
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
 * 文字+编辑框(请输入)
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolder1 extends BaseViewHolder{

    protected TextView mTxtName;
    protected EditText mTxtContent;

    public ViewHolder1(Context context) {
        mContext = context;
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
            mTxtContent.setHint("请输入"+mTopBtn.mTemplate10Been.get(position).name);
        } else {
            mTxtContent.setText(template10Bean.showValue);
        }
        mTxtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                template10Bean.showValue = s.toString();
                template10Bean.content = s.toString();
            }
        });
    }
}

package com.onesoft.digitaledu.view.template.viewholderadd;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;

/**
 * //文字+"REMARK
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolder7 extends BaseViewHolder {

    protected EditText mTxtContent;

    public ViewHolder7(Context context) {
        mContext = context;
    }

    public View createView( ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_7, parent, false);
        mTxtContent = (EditText) view.findViewById(R.id.edit_remark);
        return view;
    }

    public void bindView(final TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
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

package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder7;

/**
 * Remark
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolderEdit7 extends ViewHolder7 {

    public ViewHolderEdit7(Context context) {
        super(context);
    }

    public void bindView(final TopBtn mTopBtn, int position) {
        final Template10Bean mTemplate10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtContent.setText(TextUtils.isEmpty(mTemplate10Bean.showEditValue) ? mTemplate10Bean.originValue.toString() : mTemplate10Bean.showEditValue);
        if (TextUtils.isEmpty(mTemplate10Bean.content)) {
            mTemplate10Bean.content = mTemplate10Bean.originValue.toString();
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
                mTemplate10Bean.showEditValue = s.toString();
                mTemplate10Bean.content = s.toString();
            }
        });
    }
}

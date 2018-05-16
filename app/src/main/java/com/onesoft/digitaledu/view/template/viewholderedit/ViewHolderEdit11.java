package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder11;

/**
 * Remark
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolderEdit11 extends ViewHolder11 {

    public ViewHolderEdit11(Context context) {
        super(context);
    }

    @Override
    protected void setData(TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showEditValue) ? template10Bean.originValue.toString() : template10Bean.showEditValue);
        if (TextUtils.isEmpty(template10Bean.content)){
            template10Bean.content = template10Bean.originValue.toString();
        }
    }

    @Override
    protected void updateTemplateBean(Template10Bean template10Bean,  StringBuilder builder, StringBuilder idBuilder ) {
        template10Bean.showEditValue = builder.deleteCharAt(builder.length() - 1).toString();
        template10Bean.content = idBuilder.deleteCharAt(idBuilder.length() - 1).toString();
    }
}

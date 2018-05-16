package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.Department;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder22;

/**
 * 文字+"必选"+右边箭头，弹出弹框 所属部门
 * Created by Jayden on 2016/12/22.
 */

public class ViewHolderEdit22 extends ViewHolder22 {

    public ViewHolderEdit22(Context context) {
        super(context);
    }

    @Override
    protected void setData(TopBtn mTopBtn, int position) {
        Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showEditValue) ? template10Bean.originValue.toString() : template10Bean.showEditValue);
        if (TextUtils.isEmpty(template10Bean.content)) {
            template10Bean.content = template10Bean.originValue.toString();
        }
    }

    protected void updateTemplateBean(Template10Bean template10Bean, Department bean) {
        template10Bean.showEditValue = bean.depart_name;
        template10Bean.content = bean.depart_id;
    }

}

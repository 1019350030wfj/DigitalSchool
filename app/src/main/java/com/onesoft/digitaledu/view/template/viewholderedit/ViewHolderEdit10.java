package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.ParentDepartment;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder10;

/**
 * 编辑的时候要把originValue复制给content
 * 文字+"必选"+右边箭头，弹出弹框 上级部门
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolderEdit10 extends ViewHolder10 {

    public ViewHolderEdit10(Context context) {
        super(context);
    }

    @Override
    protected void setData(TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showEditValue) ? template10Bean.originValue.toString() : template10Bean.showEditValue);
        if (TextUtils.isEmpty(template10Bean.content)) {
            template10Bean.content = template10Bean.originValue.toString();
        }
    }

    @Override
    protected void updateTemplateBean(Template10Bean template10Bean, ParentDepartment bean) {
        template10Bean.showEditValue = bean.text;
        template10Bean.content = bean.parent_depart;
    }
}

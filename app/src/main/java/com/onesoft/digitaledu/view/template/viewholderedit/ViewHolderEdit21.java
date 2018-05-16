package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder21;

/**
 * 文字+"必选"+右边箭头，弹出弹框 楼层状态
 * 0：停用；  1：空闲；  2：使用中；  3：修建中
 * Created by Jayden on 2016/12/22.
 */

public class ViewHolderEdit21 extends ViewHolder21 {

    public ViewHolderEdit21(Context context) {
        super(context);
    }

    @Override
    protected void setData(Template10Bean template10Bean) {
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showEditValue) ? template10Bean.originValue.toString() : template10Bean.showEditValue);
        if (TextUtils.isEmpty(template10Bean.content)) {
            template10Bean.content = template10Bean.originValue.toString();
        }
    }

    @Override
    protected void updateTemplateBean(Template10Bean template10Bean, SingleSelectBean bean) {
        template10Bean.showEditValue = bean.name;
        template10Bean.content = bean.id;
    }
}

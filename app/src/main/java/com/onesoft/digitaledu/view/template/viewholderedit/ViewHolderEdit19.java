package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder19;

/**
 * 文字+"必选"+右边箭头，弹出弹框 使用类别
 * 1：教学  2：实训  3：宿舍  4：食堂  5：办公  6：综合
 * Created by Jayden on 2016/12/22.
 */

public class ViewHolderEdit19 extends ViewHolder19 {

    public ViewHolderEdit19(Context context) {
        super(context);
    }

    @Override
    protected void setData(Template10Bean template10Bean) {
        mTxtContent.setText(template10Bean.originValue.toString());
        if (TextUtils.isEmpty(template10Bean.content)) {
            template10Bean.content = template10Bean.originValue.toString();
        }
    }

    protected void updateTemplateBean(Template10Bean template10Bean, SingleSelectBean bean) {
        template10Bean.originValue = bean.name;
        template10Bean.content = bean.id;
    }
}

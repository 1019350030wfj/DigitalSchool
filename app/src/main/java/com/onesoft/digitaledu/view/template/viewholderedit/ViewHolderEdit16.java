package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder16;

/**
 * 文字+"必选"+右边箭头，弹出弹框 使用分类别
 * 1：教学  2：实训  3：宿舍  4：食堂  5：办公  6：综合
 * 1：教学A   2：教学B   3：实训A  4：实训B   5：宿舍A  6：宿舍B  
 * 7：食堂A  8：食堂B  9：办公A  10：办公B  11：综合A   12：综合B
 * Created by Jayden on 2016/12/22.
 */

public class ViewHolderEdit16 extends ViewHolder16 {

    public ViewHolderEdit16(Context context) {
        super(context);
    }

    @Override
    public void setData(Template10Bean template10Bean) {
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showEditValue) ? template10Bean.originValue.toString() : template10Bean.showEditValue);
        if (TextUtils.isEmpty(template10Bean.content)){
            template10Bean.content = template10Bean.originValue.toString();
        }
    }

    protected void updateTemplateBean(Template10Bean template10Bean, SingleSelectBean bean) {
        template10Bean.showEditValue = bean.name;
        template10Bean.content = bean.id;
    }
}

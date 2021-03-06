package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.ClassroomType;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder20;

/**
 * 文字+"必选"+右边箭头，弹出弹框 教室类型
 * Created by Jayden on 2016/12/22.
 */

public class ViewHolderEdit20 extends ViewHolder20 {

    public ViewHolderEdit20(Context context) {
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

    @Override
    protected void updateTemplateBean(Template10Bean template10Bean, ClassroomType bean) {
        template10Bean.showEditValue = bean.type_name;
        template10Bean.content = bean.room_type;
    }

}

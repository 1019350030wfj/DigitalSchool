package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.SelectDistrict;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder15;

/**
 * 文字+"必选"+右边箭头，弹出弹框 所属校区
 * Created by Jayden on 2016/12/22.
 */
public class ViewHolderEdit15 extends ViewHolder15 {

    public ViewHolderEdit15(Context context) {
        super(context);
    }

    @Override
    protected void setData(TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtContent.setText(template10Bean.originValue.toString());
        if (TextUtils.isEmpty(template10Bean.content)) {
            template10Bean.content = template10Bean.originValue.toString();
        }
    }

    protected void updateTemplateBean(Template10Bean template10Bean, SelectDistrict bean) {
        template10Bean.originValue = bean.district_name;
        template10Bean.content = bean.district_id;
    }

}

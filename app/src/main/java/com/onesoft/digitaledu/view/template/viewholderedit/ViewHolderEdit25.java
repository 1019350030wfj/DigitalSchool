package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder25;

import java.io.File;

/**
 * 文字+"必选"+右边箭头，弹出弹框 选择文件
 * Created by Jayden on 2017/01/10.
 */
public class ViewHolderEdit25 extends ViewHolder25 {

    public ViewHolderEdit25(Context context) {
        super(context);
    }

    protected void setData(TopBtn mTopBtn, int position) {
        mTemplate10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtContent.setText(TextUtils.isEmpty(mTemplate10Bean.showEditValue) ? mTemplate10Bean.originValue.toString() : mTemplate10Bean.showEditValue);
        if (TextUtils.isEmpty(mTemplate10Bean.content)) {
            mTemplate10Bean.content = mTemplate10Bean.originValue.toString();
        }
    }

    protected void updateTemplateBean(String path) {
        File file = new File(path);
        mTemplate10Bean.showEditValue = file.getName();
        mTemplate10Bean.content = path;
    }

}

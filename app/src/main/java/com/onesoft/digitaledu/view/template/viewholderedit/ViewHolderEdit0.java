package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.BaseViewHolder;

/**
 * 文字+文字
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolderEdit0 extends BaseViewHolder {

    protected TextView mTxtName;
    protected TextView mTxtContent;

    public ViewHolderEdit0(Context context) {
        mContext = context;
    }

    public View createView(ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_0, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.txt_name);
        mTxtContent = (TextView) view.findViewById(R.id.edit_content);
        return view;
    }

    public void bindView(final TopBtn mTopBtn, final int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        mTxtContent.setText(template10Bean.originValue.toString());
    }

}

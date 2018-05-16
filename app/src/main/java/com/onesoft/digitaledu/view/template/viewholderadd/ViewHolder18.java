package com.onesoft.digitaledu.view.template.viewholderadd;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 文字+"必选"+右边箭头，弹出弹框 用地类别
 * 1：教育用地  2：其它用地
 * Created by Jayden on 2016/12/22.
 */

public class ViewHolder18 extends BaseViewHolder {

    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;

    protected List<SingleSelectBean> mSingleSelectBeen;

    public ViewHolder18(Context context) {
        mContext = context;
        mSingleSelectBeen = new ArrayList<>();
        mSingleSelectBeen.add(new SingleSelectBean("教育用地", "1"));
        mSingleSelectBeen.add(new SingleSelectBean("其它用地", "2"));
    }

    public View createView(ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_3, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.title);
        mTxtContent = (TextView) view.findViewById(R.id.title_content);
        img_select = (ImageView) view.findViewById(R.id.img_select);
        return view;
    }

    public void bindView(final TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        setData(template10Bean);
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(mContext) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(mContext) {
                            @Override
                            public void bindView(TextView textView, int pos) {
                                textView.setText(mSingleSelectBeen.get(pos).name);
                            }
                        };
                    }
                };
                dialog.show();
                dialog.setDatas(mSingleSelectBeen);
                dialog.setDialogListener(new SingleSelectDialog.IDialogClick<SingleSelectBean>() {
                    @Override
                    public void onConfirm(SingleSelectBean bean, int pos) {
                        updateTemplateBean(template10Bean, bean);
                        mINotifyChange.onNotifyChange();
                    }
                });
            }
        });
    }

    protected void setData(Template10Bean template10Bean) {
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showValue)
                ? mContext.getResources().getString(R.string.need_select)
                : template10Bean.showValue);
    }

    protected void updateTemplateBean(Template10Bean template10Bean, SingleSelectBean bean) {
        template10Bean.showValue = bean.name;
        template10Bean.content = bean.id;
    }
}

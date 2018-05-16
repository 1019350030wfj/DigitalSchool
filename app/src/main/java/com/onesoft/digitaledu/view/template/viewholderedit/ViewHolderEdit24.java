package com.onesoft.digitaledu.view.template.viewholderedit;

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
import com.onesoft.digitaledu.view.template.viewholderadd.BaseViewHolder;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 文字+"必选"+右边箭头，弹出弹框 是否显示
 * Created by Jayden on 2017/01/05.
 */
public class ViewHolderEdit24 extends BaseViewHolder {

    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;

    protected List<SingleSelectBean> mSingleSelectBeen;

    public ViewHolderEdit24(Context context) {
        mContext = context;
        mSingleSelectBeen = new ArrayList<>();
        mSingleSelectBeen.add(new SingleSelectBean(context.getString(R.string.yes),"1"));
        mSingleSelectBeen.add(new SingleSelectBean(context.getString(R.string.not),"0"));
    }

    public View createView( ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_3, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.title);
        mTxtContent = (TextView) view.findViewById(R.id.title_content);
        img_select = (ImageView) view.findViewById(R.id.img_select);
        return view;
    }

    public void bindView(final TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showEditValue)?template10Bean.originValue.toString():template10Bean.showEditValue);
        if (TextUtils.isEmpty(template10Bean.content)){
            template10Bean.content = template10Bean.originValue.toString();
        }
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(mContext) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(mContext){
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
                    public void onConfirm(SingleSelectBean bean,int pos) {
                        template10Bean.showEditValue = bean.name;
                        template10Bean.content = bean.id;
                        mINotifyChange.onNotifyChange();
                    }
                });
            }
        });
    }
}

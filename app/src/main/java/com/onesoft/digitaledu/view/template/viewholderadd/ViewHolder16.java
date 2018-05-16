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
 * 文字+"必选"+右边箭头，弹出弹框 使用分类别
 * 1：教学  2：实训  3：宿舍  4：食堂  5：办公  6：综合
 * 1：教学A   2：教学B   3：实训A  4：实训B   5：宿舍A  6：宿舍B  
 * 7：食堂A  8：食堂B  9：办公A  10：办公B  11：综合A   12：综合B
 * Created by Jayden on 2016/12/22.
 */

public class ViewHolder16 extends BaseViewHolder {

    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;

    protected List<List<SingleSelectBean>> mSingleSelectBeen;

    public ViewHolder16(Context context) {
        mContext = context;
        mSingleSelectBeen = new ArrayList<>();
        List<SingleSelectBean> beanList = new ArrayList<>();
        beanList.add(new SingleSelectBean("教学A","1"));
        beanList.add(new SingleSelectBean("教学B","2"));
        List<SingleSelectBean> beanList1 = new ArrayList<>();
        beanList1.add(new SingleSelectBean("实训A","3"));
        beanList1.add(new SingleSelectBean("实训B","4"));
        List<SingleSelectBean> beanList2 = new ArrayList<>();
        beanList2.add(new SingleSelectBean("宿舍A","5"));
        beanList2.add(new SingleSelectBean("宿舍B","6"));
        List<SingleSelectBean> beanList3 = new ArrayList<>();
        beanList3.add(new SingleSelectBean("食堂A","7"));
        beanList3.add(new SingleSelectBean("食堂B","8"));
        List<SingleSelectBean> beanList4 = new ArrayList<>();
        beanList4.add(new SingleSelectBean("办公A","9"));
        beanList4.add(new SingleSelectBean("办公B","10"));
        List<SingleSelectBean> beanList5 = new ArrayList<>();
        beanList5.add(new SingleSelectBean("综合A","11"));
        beanList5.add(new SingleSelectBean("综合B","12"));
        mSingleSelectBeen.add(beanList);
        mSingleSelectBeen.add(beanList1);
        mSingleSelectBeen.add(beanList2);
        mSingleSelectBeen.add(beanList3);
        mSingleSelectBeen.add(beanList4);
        mSingleSelectBeen.add(beanList5);
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
        setData(template10Bean);
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(mContext) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(mContext){
                            @Override
                            public void bindView(TextView textView,int pos) {
                                textView.setText(mSingleSelectBeen.get(Integer.parseInt(template10Bean.extend)).get(pos).name);
                            }
                        };
                    }
                };
                dialog.show();
                dialog.setDatas(mSingleSelectBeen.get(Integer.parseInt(template10Bean.extend)));
                dialog.setDialogListener(new SingleSelectDialog.IDialogClick<SingleSelectBean>() {
                    @Override
                    public void onConfirm(SingleSelectBean bean,int pos) {
                        updateTemplateBean(template10Bean,bean);
                        mINotifyChange.onNotifyChange();
                    }
                });
            }
        });
    }

    public void setData(Template10Bean template10Bean) {
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showValue)
                ?mContext.getResources().getString(R.string.need_select)
                :template10Bean.showValue);
    }

    protected void updateTemplateBean(Template10Bean template10Bean, SingleSelectBean bean) {
        template10Bean.showValue = bean.name;
        template10Bean.content = bean.id;
    }
}

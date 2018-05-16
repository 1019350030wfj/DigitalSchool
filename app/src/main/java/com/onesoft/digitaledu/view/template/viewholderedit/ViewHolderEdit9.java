package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.TemplateCommonPresenter;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder9;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;

import java.util.List;

/**
 * Remark
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolderEdit9 extends ViewHolder9 {

    public ViewHolderEdit9(Context context) {
        super(context);
    }

    public void bindView(final TopBtn mTopBtn, final int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        mTxtContent.setText(TextUtils.isEmpty(template10Bean.showEditValue)?template10Bean.originValue.toString():template10Bean.showEditValue);
        if (TextUtils.isEmpty(template10Bean.content)){
            template10Bean.content = template10Bean.originValue.toString();
        }
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRoles == null) {//服务器获取数据
                    TemplateCommonPresenter commonPresenter = new TemplateCommonPresenter(mContext);
                    commonPresenter.getSelectNation(new TemplateCommonPresenter.ISelectNation() {
                        @Override
                        public void onGetSelectRole(List<SingleSelectBean> roles) {
                            mRoles = roles;
                            showSelectRoleDialog(mTopBtn, position);
                        }
                    });
                } else {
                    showSelectRoleDialog(mTopBtn, position);
                }
            }
        });
    }

    private void showSelectRoleDialog(TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(mContext) {
            @Override
            public SingleSelectAdapter getAdapter() {
                return new SingleSelectAdapter(mContext){
                    @Override
                    public void bindView(TextView textView, int pos) {
                        textView.setText(mRoles.get(pos).name);
                    }
                };
            }
        };
        dialog.show();
        dialog.setDatas(mRoles);
        dialog.setDialogListener(new SingleSelectDialog.IDialogClick<SingleSelectBean>() {
            @Override
            public void onConfirm(SingleSelectBean bean,int pos) {
                template10Bean.showEditValue = bean.name;
                template10Bean.content = bean.id;
                mINotifyChange.onNotifyChange();
            }
        });
    }
}

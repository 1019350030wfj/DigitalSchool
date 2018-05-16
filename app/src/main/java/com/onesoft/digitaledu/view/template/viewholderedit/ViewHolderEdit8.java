package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.SelectRole;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.TemplateCommonPresenter;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.view.iview.template.ICommonView;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder8;
import com.onesoft.digitaledu.widget.dialog.MoreSelectDialog;
import com.onesoft.digitaledu.widget.dialog.SelectRoleAdapter;

import java.util.List;

/**
 * Remark
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolderEdit8 extends ViewHolder8 {

    public ViewHolderEdit8(Context context) {
        super(context);
    }

    @Override
    public void bindView(final TopBtn mTopBtn, final int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        mTxtContent.setText(template10Bean.originValue.toString());
        if (TextUtils.isEmpty(template10Bean.content)){
            template10Bean.content = template10Bean.originValue.toString();
        }
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRoles == null) {//服务器获取数据
                    TemplateCommonPresenter commonPresenter = new TemplateCommonPresenter(mContext);
                    commonPresenter.getSelectRole(new ICommonView() {
                        @Override
                        public void onGetSelectRole(List<SelectRole> roles) {
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
        MoreSelectDialog<SelectRole> roleDialog = new MoreSelectDialog<SelectRole>(mContext) {
            @Override
            public BaseAbsListAdapter getAdapter() {
                return new SelectRoleAdapter<SelectRole>(mContext, true);
            }
        };
        roleDialog.show();
        roleDialog.setTitle(mContext.getString(R.string.select_role));
        roleDialog.setDatas(mRoles);
        roleDialog.setDialogListener(new MoreSelectDialog.IDialogClick() {
            @Override
            public void onConfirm() {
                StringBuilder builder = new StringBuilder();
                StringBuilder idBuilder = new StringBuilder();
                for (SelectRole item : mRoles) {
                    if (item.isSelect) {
                        builder.append(item.role_name).append("|");
                        idBuilder.append(item.id).append(",");
                    }
                }
                if (builder.length() > 0) {
                    template10Bean.originValue = builder.deleteCharAt(builder.length() - 1).toString();
                    template10Bean.content = idBuilder.deleteCharAt(idBuilder.length() - 1).toString();
                    mINotifyChange.onNotifyChange();
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }
}

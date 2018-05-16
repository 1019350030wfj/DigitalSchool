package com.onesoft.digitaledu.view.template.viewholderadd;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.SelectRole;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.TemplateCommonPresenter;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.view.iview.template.ICommonView;
import com.onesoft.digitaledu.widget.dialog.MoreSelectDialog;
import com.onesoft.digitaledu.widget.dialog.SelectRoleAdapter;

import java.util.List;

/**
 * 文字+"必选"+右边箭头，弹出弹框 用户角色
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolder8 extends BaseViewHolder{

    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;

    protected List<SelectRole> mRoles;

    public ViewHolder8(Context context) {
        mContext = context;
    }

    @Override
    public View createView( ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_3, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.title);
        mTxtContent = (TextView) view.findViewById(R.id.title_content);
        img_select = (ImageView) view.findViewById(R.id.img_select);
        return view;
    }

    @Override
    public void bindView(final TopBtn mTopBtn, final int position) {
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        mTxtContent.setText(TextUtils.isEmpty(mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key).showValue)
                ?("请选择"+mTopBtn.mTemplate10Been.get(position).name)
                :mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key).showValue);
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
                    template10Bean.showValue = builder.deleteCharAt(builder.length() - 1).toString();
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

package com.onesoft.digitaledu.view.template.viewholderadd;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.SelectMember;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.TemplateCommonPresenter;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.widget.dialog.MoreSelectDialog;
import com.onesoft.digitaledu.widget.dialog.SelectRoleAdapter;

import java.util.List;

/**
 * 文字+"必选"+右边箭头，弹出弹框 下属成员（选择成员）
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolder11 extends BaseViewHolder {

    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;

    protected List<SelectMember> mRoles;

    public ViewHolder11(Context context) {
        mContext = context;
    }

    @Override
    public View createView(ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_3, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.title);
        mTxtContent = (TextView) view.findViewById(R.id.title_content);
        img_select = (ImageView) view.findViewById(R.id.img_select);
        return view;
    }

    @Override
    public void bindView(final TopBtn mTopBtn, final int position) {
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        setData(mTopBtn, position);
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRoles == null) {//服务器获取数据
                    TemplateCommonPresenter commonPresenter = new TemplateCommonPresenter(mContext);
                    commonPresenter.getSelectMember(new TemplateCommonPresenter.ISelectMember() {
                        @Override
                        public void onGetSelectMember(List<SelectMember> roles) {
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

    protected void setData(TopBtn mTopBtn, int position) {
        mTxtContent.setText(TextUtils.isEmpty(mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key).showValue)
                ? mContext.getString(R.string.need_select)
                : mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key).showValue);
    }

    private void showSelectRoleDialog(TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        MoreSelectDialog<SelectMember> roleDialog = new MoreSelectDialog<SelectMember>(mContext) {
            @Override
            public BaseAbsListAdapter getAdapter() {
                return new SelectRoleAdapter<SelectMember>(mContext, true) {
                    @Override
                    public void bindView(int position, SelectRoleAdapter.ViewHolder viewHolder) {
                        final SelectMember item = mRoles.get(position);
                        viewHolder.select.setSelected(item.isSelect);
                        final ViewHolder finalViewHolder = viewHolder;
                        viewHolder.select.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//改变状态
                                if (finalViewHolder.select.isSelected()) {
                                    finalViewHolder.select.setSelected(false);
                                    item.isSelect = false;
                                } else {
                                    finalViewHolder.select.setSelected(true);
                                    item.isSelect = true;
                                }
                            }
                        });
                        viewHolder.name.setText(item.name);
                    }
                };
            }
        };
        roleDialog.show();
        roleDialog.setTitle(mContext.getString(R.string.select_member));
        roleDialog.setDatas(mRoles);
        roleDialog.setDialogListener(new MoreSelectDialog.IDialogClick() {
            @Override
            public void onConfirm() {
                StringBuilder builder = new StringBuilder();
                StringBuilder idBuilder = new StringBuilder();
                for (SelectMember item : mRoles) {
                    if (item.isSelect) {
                        builder.append(item.name).append("|");
                        idBuilder.append(item.id).append(",");
                    }
                }
                if (builder.length() > 0) {
                    updateTemplateBean(template10Bean, builder, idBuilder);
                    mINotifyChange.onNotifyChange();
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    protected void updateTemplateBean(Template10Bean template10Bean, StringBuilder builder, StringBuilder idBuilder) {
        template10Bean.showValue = builder.deleteCharAt(builder.length() - 1).toString();
        template10Bean.content = idBuilder.deleteCharAt(idBuilder.length() - 1).toString();
    }

}

package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.SelectRole;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.presenter.template.TemplateCommonPresenter;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.view.iview.template.ICommonView;
import com.onesoft.digitaledu.widget.dialog.MoreSelectDialog;
import com.onesoft.digitaledu.widget.dialog.SelectRoleAdapter;

import java.util.List;

import static com.onesoft.digitaledu.app.Constant.LAYOUT_STYLE_SELECT;
import static com.onesoft.digitaledu.app.Constant.LAYOUT_STYLE_TEXT;

/**
 * 模版23，已分配权限编辑  动态模版
 * Created by Jayden on 2016/11/22.
 */

public class Template23Adapter extends BaseAbsListAdapter<Template10Bean> {

    private String mSeleteRole;//选择角色结果， 模版23
    private String mSeleteRoleId;//选择角色结果， 模版23
    private List<SelectRole> mRoles;

    public Template23Adapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        switch (getItem(position).mLayoutType) {
            case LAYOUT_STYLE_TEXT: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_0, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.name = (TextView) convertView.findViewById(R.id.txt_name);
                    viewHolder.content = (TextView) convertView.findViewById(R.id.edit_content);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final Template10Bean bean = getItem(position);
                viewHolder.name.setText(bean.name);
                viewHolder.content.setText(bean.originValue.toString());
                break;
            }
            case LAYOUT_STYLE_SELECT: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_3, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                    viewHolder.titleContent = (TextView) convertView.findViewById(R.id.title_content);
                    viewHolder.mImgSelect = (ImageView) convertView.findViewById(R.id.img_select);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final Template10Bean bean = getItem(position);
                viewHolder.title.setText(bean.name);
                viewHolder.titleContent.setText(TextUtils.isEmpty(mSeleteRole) ? bean.originValue.toString() : mSeleteRole);
                viewHolder.mImgSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mRoles == null) {//服务器获取数据
                            TemplateCommonPresenter commonPresenter = new TemplateCommonPresenter(mContext);
                            commonPresenter.getSelectRole(new ICommonView() {
                                @Override
                                public void onGetSelectRole(List<SelectRole> roles) {
                                    mRoles = roles;
                                    showSelectRoleDialog(bean);
                                }
                            });
                        } else {
                            showSelectRoleDialog(bean);
                        }
                    }
                });
                break;
            }
        }
        return convertView;
    }

    private void showSelectRoleDialog(final Template10Bean bean) {
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
                    mSeleteRole = builder.deleteCharAt(builder.length() - 1).toString();
                    bean.key = idBuilder.deleteCharAt(idBuilder.length() - 1).toString();
                    mSeleteRoleId = idBuilder.deleteCharAt(idBuilder.length() - 1).toString();
                } else {
                    bean.key = "";
                    mSeleteRole = "";
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public String getSelectRoleId() {
        return mSeleteRoleId;
    }

    private class ViewHolder {
        TextView name;
        TextView content;
        /*===select===*/
        TextView title;
        TextView titleContent;
        ImageView mImgSelect;
        /*===select===*/
        EditText TextView;
        EditText remark;
    }
}

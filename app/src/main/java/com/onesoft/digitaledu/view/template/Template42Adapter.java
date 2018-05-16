package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.onesoft.digitaledu.app.Constant;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.view.template.viewholderadd.BaseViewHolder;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit0;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit1;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit10;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit11;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit15;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit16;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit17;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit18;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit19;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit20;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit21;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit22;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit23;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit24;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit25;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit3;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit4;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit5;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit6;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit7;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit8;
import com.onesoft.digitaledu.view.template.viewholderedit.ViewHolderEdit9;

import static com.onesoft.digitaledu.app.Constant.LAYOUT_STYLE_REMARK;

/**
 * 编辑  动态模版
 * 要设置setTopBtn
 * Created by Jayden on 2016/12/20.
 */

public class Template42Adapter extends BaseAbsListAdapter<Template10Bean> {

    private TopBtn mTopBtn;

    public void setTopBtn(TopBtn topBtn) {
        mTopBtn = topBtn;
    }

    public Template42Adapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BaseViewHolder viewHolder = null;
        switch (getItem(position).mLayoutType) {
            case Constant.LAYOUT_STYLE_SELECT_FILE: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit25(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit25) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_IS_SHOW: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit24(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit24) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_MENU_PARENT: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit23(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit23) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_DEPARTMENT: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit22(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit22) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_CLASSROOM: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit20(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit20) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_FLOOR: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit21(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit21) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_DISTRICT: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit15(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit15) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_CLASSIFY: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit16(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit16) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_BUILDING: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit17(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit17) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_LAND_USE: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit18(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit18) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_USE_TYPE: {
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit19(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit19) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_MEMBER: {//选择成员
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit11(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit11) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_TEXT: {//文字+文字
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit0(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit0) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_USERTYPE: {//用户类型
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit3(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit3) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_EDIT_NEED: {//必填
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit4(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit4) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_EDIT: {//请输入
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit1(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit1) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_BIRTHDAY: {//出生年月
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit5(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit5) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_SEX: {//性别
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit6(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit6) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_NATION: {//汉族
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit9(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit9) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_USER_ROLE: {//用户角色
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit8(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit8) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case LAYOUT_STYLE_REMARK: {//Remark
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit7(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit7) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_PARENT_DEPARTMENT: {//上级部门
                if (convertView == null) {
                    viewHolder = new ViewHolderEdit10(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderEdit10) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }

        }
        if (viewHolder != null) {
            viewHolder.setINotifyChange(new BaseViewHolder.INotifyChange() {
                @Override
                public void onNotifyChange() {
                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }
}

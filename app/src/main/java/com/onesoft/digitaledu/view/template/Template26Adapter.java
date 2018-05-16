package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.onesoft.digitaledu.app.Constant;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.view.template.viewholderadd.BaseViewHolder;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder1;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder10;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder12;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder13;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder14;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder15;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder16;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder17;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder18;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder19;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder20;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder21;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder22;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder25;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder3;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder4;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder5;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder6;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder7;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder8;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder9;

import static com.onesoft.digitaledu.app.Constant.LAYOUT_STYLE_REMARK;

/**
 * 添加  动态模版
 * 要设置setTopBtn
 * Created by Jayden on 2016/12/20.
 */

public class Template26Adapter extends BaseAbsListAdapter<Template10Bean> {

    private TopBtn mTopBtn;

    public void setTopBtn(TopBtn topBtn) {
        mTopBtn = topBtn;
    }

    public Template26Adapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BaseViewHolder viewHolder = null;
        switch (getItem(position).mLayoutType) {
            case Constant.LAYOUT_STYLE_SELECT_FILE: {
                if (convertView == null) {
                    viewHolder = new ViewHolder25(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder25) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_DISTRICT: {
                if (convertView == null) {
                    viewHolder = new ViewHolder15(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder15) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_FLOOR: {
                if (convertView == null) {
                    viewHolder = new ViewHolder21(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder21) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_CLASSIFY: {
                if (convertView == null) {
                    viewHolder = new ViewHolder16(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder16) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_BUILDING: {
                if (convertView == null) {
                    viewHolder = new ViewHolder17(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder17) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_LAND_USE: {
                if (convertView == null) {
                    viewHolder = new ViewHolder18(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder18) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_USE_TYPE: {
                if (convertView == null) {
                    viewHolder = new ViewHolder19(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder19) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_CLASSROOM: {
                if (convertView == null) {
                    viewHolder = new ViewHolder20(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder20) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_DEPARTMENT: {
                if (convertView == null) {
                    viewHolder = new ViewHolder22(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder22) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_USER_TREE: {//文字+"必选"+右边加号，弹出弹框 用户树
                if (convertView == null) {
                    viewHolder = new ViewHolder13(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder13) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_EDIT_LIMIT: {//文字+编辑框(输入标题不超过12个字)
                if (convertView == null) {
                    viewHolder = new ViewHolder14(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder14) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_TEXT_DEFAULT: {//类型 + 通知
                if (convertView == null) {
                    viewHolder = new ViewHolder12(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder12) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_PARENT_DEPARTMENT: {//上级部门
                if (convertView == null) {
                    viewHolder = new ViewHolder10(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder10) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_USERTYPE: {//用户类型
                if (convertView == null) {
                    viewHolder = new ViewHolder3(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder3) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_EDIT_NEED: {//必填
                if (convertView == null) {
                    viewHolder = new ViewHolder4(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder4) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_EDIT: {//请输入
                if (convertView == null) {
                    viewHolder = new ViewHolder1(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder1) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_BIRTHDAY: {//出生年月
                if (convertView == null) {
                    viewHolder = new ViewHolder5(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder5) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_SEX: {//性别
                if (convertView == null) {
                    viewHolder = new ViewHolder6(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder6) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_NATION: {//汉族
                if (convertView == null) {
                    viewHolder = new ViewHolder9(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder9) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case Constant.LAYOUT_STYLE_SELECT_NEED_USER_ROLE: {//用户角色
                if (convertView == null) {
                    viewHolder = new ViewHolder8(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder8) convertView.getTag();
                }
                viewHolder.bindView(mTopBtn, position);
                break;
            }
            case LAYOUT_STYLE_REMARK: {//Remark
                if (convertView == null) {
                    viewHolder = new ViewHolder7(mContext);
                    convertView = viewHolder.createView(parent);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder7) convertView.getTag();
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

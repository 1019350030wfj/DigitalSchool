package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

/**
 * 模版10，校区管理  添加  动态模版
 * Created by Jayden on 2016/11/22.
 */

public class Template11Adapter extends BaseAbsListAdapter<Template10Bean> {

    public static final int ITEM_TYPE_STRING = 0;
    public static final int ITEM_TYPE_REMARK = 1;

    public Template11Adapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        switch (getItemViewType(position)) {
            case ITEM_TYPE_STRING: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_4, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.name = (TextView) convertView.findViewById(R.id.txt_name);
                    viewHolder.content = (EditText) convertView.findViewById(R.id.edit_content);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final Template10Bean bean = getItem(position);
                viewHolder.name.setText(bean.name);
                if (bean.originValue instanceof String){
                    viewHolder.content.setText(bean.originValue.toString());
                } else {
                    viewHolder.content.setText("请输入"+bean.name);
                }

                if (position == 0){
                    viewHolder.content.setFocusable(false);
                } else {
                    viewHolder.content.setFocusable(true);
                }

                viewHolder.content.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        bean.originValue = s.toString();
                    }
                });
                break;
            }
            case ITEM_TYPE_REMARK: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_7, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.remark = (EditText) convertView.findViewById(R.id.edit_remark);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final Template10Bean bean = getItem(position);
                viewHolder.remark.setText(bean.originValue.toString());
                viewHolder.remark.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        bean.originValue = s.toString();
                    }
                });
                break;
            }

        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {//最后一个是标注
            return ITEM_TYPE_REMARK;
        }
        return ITEM_TYPE_STRING;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    private class ViewHolder {
        TextView name;
        EditText content;
        EditText remark;
    }
}

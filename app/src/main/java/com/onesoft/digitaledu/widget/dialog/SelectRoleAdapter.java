package com.onesoft.digitaledu.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.SelectRole;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

/**
 * Created by Jayden on 2016/11/24.
 */

public class SelectRoleAdapter<T> extends BaseAbsListAdapter<T> {

    public SelectRoleAdapter(Context context, boolean isShow) {
        super(context);
        this.isShow = isShow;
    }

    private boolean isShow;

    public void setShow(boolean show) {
        if (isShow != show) {
            isShow = show;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_operation_btn, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.select = (ImageView) convertView.findViewById(R.id.img_select);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindView(position, viewHolder);
        return convertView;
    }

    public void bindView(int position, ViewHolder viewHolder) {
        final SelectRole item = (SelectRole) getItem(position);
        if (isShow) {
            viewHolder.select.setVisibility(View.VISIBLE);
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
        } else {
            viewHolder.select.setVisibility(View.INVISIBLE);
        }
        viewHolder.name.setText(item.role_name);
    }

    public static class ViewHolder {
        public TextView name;
        public ImageView select;
    }
}

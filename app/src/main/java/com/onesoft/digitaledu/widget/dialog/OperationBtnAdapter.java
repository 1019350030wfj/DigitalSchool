package com.onesoft.digitaledu.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.OperationBtnItem;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

import java.util.ArrayList;

/**
 * Created by Jayden on 2016/11/24.
 */

public class OperationBtnAdapter extends BaseAbsListAdapter<OperationBtnItem> {

    public OperationBtnAdapter(Context context,boolean isShow) {
        super(context);
        this.isShow = isShow;
    }

    private ArrayList<String> mOperatorBtn;
    private boolean isShow;

    public void setShow(boolean show) {
        if (isShow != show) {
            isShow = show;
            notifyDataSetChanged();
        }
    }

    public void setOperatorBtn(ArrayList<String> operatorBtn) {
        this.mOperatorBtn = operatorBtn;
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
        final OperationBtnItem item = getItem(position);
        if (isShow) {
            viewHolder.select.setVisibility(View.VISIBLE);
            viewHolder.select.setSelected(mOperatorBtn.contains(item.id));
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//改变状态
                    if (finalViewHolder.select.isSelected()) {
                        finalViewHolder.select.setSelected(false);
                        mOperatorBtn.remove(item.id);
                    } else {
                        finalViewHolder.select.setSelected(true);
                        mOperatorBtn.add(item.id);
                    }
                }
            });
        } else {
            viewHolder.select.setVisibility(View.INVISIBLE);
        }
        viewHolder.name.setText(item.name);
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView select;
    }
}

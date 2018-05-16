package com.onesoft.digitaledu.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

/**
 * 单选项
 * Created by Jayden on 2016/11/24.
 */

public class SingleSelectAdapter<T> extends BaseAbsListAdapter<T> {

    public SingleSelectAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_single_select, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       bindView(viewHolder.name,position);
        return convertView;
    }

    public void bindView(TextView textView,int pos){

    }

    private static class ViewHolder {
        TextView name;
    }
}

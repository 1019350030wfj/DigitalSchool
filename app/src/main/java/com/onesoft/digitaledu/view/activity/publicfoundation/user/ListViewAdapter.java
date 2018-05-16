package com.onesoft.digitaledu.view.activity.publicfoundation.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

/**
 * Created by Jayden on 2016/11/23.
 */

public class ListViewAdapter extends BaseAbsListAdapter<ListBean> {
    public ListViewAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(getItem(position).name);
        return convertView;
    }

    private static class ViewHolder{
        TextView mTextView;
    }
}

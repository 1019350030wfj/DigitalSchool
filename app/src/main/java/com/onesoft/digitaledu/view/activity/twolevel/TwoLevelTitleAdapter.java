package com.onesoft.digitaledu.view.activity.twolevel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.TwoLevelTitle;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

/**
 * Created by Jayden on 2016/10/28.
 */

public class TwoLevelTitleAdapter extends BaseAbsListAdapter<TwoLevelTitle> {

    private String mSelectId;

    public TwoLevelTitleAdapter(Context context) {
        super(context);
    }

    public void setSelectId(String selectId) {
        mSelectId = selectId;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_two_level_title, parent,false);
            viewHolder.view = convertView;
            viewHolder.indicator = convertView.findViewById(R.id.indicator);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.item_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TwoLevelTitle topDirectory = getItem(position);
        if(topDirectory.name.length() > 4){
            viewHolder.categoryName.setText(topDirectory.name.substring(0,4));
        } else {
            viewHolder.categoryName.setText(topDirectory.name);
        }

        if (topDirectory.id.equals(mSelectId)){
            viewHolder.indicator.setBackgroundColor(mContext.getResources().getColor(R.color.color_018beb));
            viewHolder.categoryName.setBackgroundColor(mContext.getResources().getColor(R.color.color_f2f2f2));
            viewHolder.categoryName.setTextColor(mContext.getResources().getColor(R.color.color_0097ff));
        } else {
            viewHolder.indicator.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            viewHolder.categoryName.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            viewHolder.categoryName.setTextColor(mContext.getResources().getColor(R.color.color_969696));
        }

        return convertView;
    }

    class ViewHolder {
        TextView categoryName;
        View indicator;
        View view;
    }
}

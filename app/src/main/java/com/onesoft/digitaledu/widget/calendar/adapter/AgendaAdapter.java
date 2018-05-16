package com.onesoft.digitaledu.widget.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

/**
 * Created by Jayden on 2016/11/24.
 */

public class AgendaAdapter extends BaseAbsListAdapter<Agenda> {

    public AgendaAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_calendar_agenda, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.txt_title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.txt_time);
            viewHolder.content = (TextView) convertView.findViewById(R.id.txt_content);
            viewHolder.mImgType = (ImageView) convertView.findViewById(R.id.img_agenda_type);
            viewHolder.viewUp = convertView.findViewById(R.id.divider_up);
            viewHolder.viewDown = convertView.findViewById(R.id.divider_down);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Agenda item = getItem(position);
        viewHolder.title.setText(item.title);
        viewHolder.time.setText(item.startTime + "-" + item.endTime);
        viewHolder.content.setText(item.notes);
        if ("1".equals(item.schedule_category_id)){//家
            viewHolder.mImgType.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.icon_calendar_gohome));
        } else if ("2".equals(item.schedule_category_id)){//工作
            viewHolder.mImgType.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.icon_calendar_jobs));
        } else if ("3".equals(item.schedule_category_id)){//学校
            viewHolder.mImgType.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.icon_calendar_school));
        } else {
            viewHolder.mImgType.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.icon_attendance_tag));
        }

        if (position == 0) {//第一项
            viewHolder.viewUp.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.viewUp.setVisibility(View.VISIBLE);
        }

        if (position == getCount() - 1){//最后一项
            viewHolder.viewDown.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.viewDown.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView time;
        TextView content;
        ImageView mImgType;
        View viewUp;
        View viewDown;
    }
}

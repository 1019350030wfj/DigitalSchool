package com.onesoft.digitaledu.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.TeacherInfo;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;

import static com.onesoft.digitaledu.R.id.avatar;

/**
 * 教师列表
 * Created by Jayden on 2016/11/30.
 */

public class SelectTeacherAdapter extends BaseAbsListAdapter<TeacherInfo> {
    private String mSearchContent;

    public void setSearchContent(String searchContent) {
        mSearchContent = searchContent;
    }

    public SelectTeacherAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_teacher, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.message = (TextView) convertView.findViewById(R.id.message);
            viewHolder.avatar = (ImageView) convertView.findViewById(avatar);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.container);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final TeacherInfo itemData = getItem(position);

        viewHolder.title.setText(itemData.the_teacher_id);
        viewHolder.message.setText("职称："+itemData.jobtitle);
        if (TextUtils.isEmpty(mSearchContent)) {//为空说明不是搜索页面
            viewHolder.name.setText(itemData.name);
        } else {
            ViewUtil.setPartOfTextViewColor(viewHolder.name,itemData.name, mSearchContent);
        }
        return convertView;
    }


    private static class ViewHolder {
        public TextView name;
        public TextView title;
        public TextView message;
        public ImageView avatar;
        public RelativeLayout relativeLayout;
    }
}

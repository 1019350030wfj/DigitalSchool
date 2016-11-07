package com.onesoft.digitaledu.view.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.TopDirectory;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.netlibrary.utils.ImageHandler;

/**
 * Created by Jayden on 2016/10/28.
 */

public class TopDirectoryAdapter extends BaseAbsListAdapter<TopDirectory> {

    public TopDirectoryAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_top_directory, null);
            viewHolder.categoryImg = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TopDirectory topDirectory = getItem(position);
        ImageHandler.getImage((Activity) mContext, viewHolder.categoryImg, topDirectory.imgUrl);
        viewHolder.categoryName.setText(topDirectory.name);
        return convertView;
    }

    class ViewHolder {
        ImageView categoryImg;
        TextView categoryName;
    }
}

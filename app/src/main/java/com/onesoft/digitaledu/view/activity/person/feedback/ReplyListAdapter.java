package com.onesoft.digitaledu.view.activity.person.feedback;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Feedback;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.widget.CircleImageView;
import com.onesoft.digitaledu.widget.EmoticonsTextView;
import com.onesoft.netlibrary.utils.ImageHandler;


/**
 * 回复评论列表
 */
public class ReplyListAdapter extends BaseAbsListAdapter<Feedback> {
    private int divider = 0;

    public ReplyListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_reply_feedback, viewGroup, false);
            holder = new ViewHolder();
            holder.avater = (CircleImageView) view.findViewById(R.id.image);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.time = (TextView) view.findViewById(R.id.tv_time);
            holder.content = (EmoticonsTextView) view.findViewById(R.id.content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Feedback item = getItem(i);
        if (item != null) {
            ImageHandler.getAvater((Activity) mContext, holder.avater, item.photo);
            holder.name.setText(item.nickname);
            holder.time.setText(item.create_time);
            holder.content.setText(item.content);
        }
        return view;
    }

    private class ViewHolder {
        View layout;
        CircleImageView avater;
        TextView name;
        TextView time;
        EmoticonsTextView content;
    }
}

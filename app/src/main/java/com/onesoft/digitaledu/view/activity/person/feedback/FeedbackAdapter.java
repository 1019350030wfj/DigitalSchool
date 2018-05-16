package com.onesoft.digitaledu.view.activity.person.feedback;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Feedback;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.utils.Utils;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.widget.CircleImageView;
import com.onesoft.digitaledu.widget.EmoticonsTextView;
import com.onesoft.digitaledu.widget.LayoutListView;
import com.onesoft.netlibrary.utils.ImageHandler;

/**
 * Created by Jayden on 2016/11/18.
 */

public class FeedbackAdapter extends BaseAbsListAdapter<Feedback> {

    public FeedbackAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_feedback, null);
            holder = new ViewHolder();
            holder.avater = (CircleImageView) view.findViewById(R.id.image);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.time = (TextView) view.findViewById(R.id.tv_time);
            holder.content = (EmoticonsTextView) view.findViewById(R.id.content);
            holder.comments = (LayoutListView) view.findViewById(R.id.comment_list);
            holder.mBtnMessage = (ImageButton) view.findViewById(R.id.btn_message);
            holder.mBtnDelete = (ImageButton) view.findViewById(R.id.btn_delete);
            holder.mBtnController = view.findViewById(R.id.btn_controller);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Feedback item = getItem(position);
        if (item != null) {
            ImageHandler.getAvater((Activity) mContext, holder.avater, item.photo);
            holder.name.setText(item.nickname);
            holder.content.setText(item.content);
            holder.time.setText(item.create_time);
            holder.avater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//点击头像
                }
            });
            if ("1".equals(SPHelper.getUserId(mContext)) || item.user_id.equals(SPHelper.getUserId(mContext))) {//只有管理员才能删除所有的反馈，还有自己可以删除自己的
                holder.mBtnDelete.setVisibility(View.VISIBLE);
                holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mFeedbackListener != null) {
                            mFeedbackListener.onDelete(item);
                        }
                    }
                });
            } else {
                holder.mBtnDelete.setVisibility(View.INVISIBLE);
            }
            holder.mBtnMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mFeedbackListener != null) {
                        mFeedbackListener.onComment(item);
                    }
                }
            });
            if (Utils.isListEmpty(item.children)) {
                holder.comments.setVisibility(View.GONE);
            } else {
                holder.comments.setVisibility(View.VISIBLE);
                ReplyListAdapter adapter = new ReplyListAdapter(mContext);
                adapter.setDatas(item.children);
                holder.comments.setAdapter(adapter);
            }
        }
        return view;
    }

    private class ViewHolder {
        CircleImageView avater;
        TextView name;
        TextView time;
        EmoticonsTextView content;
        LayoutListView comments;
        ImageButton mBtnMessage;
        ImageButton mBtnDelete;
        View mBtnController;
    }

    private OnFeedbackListener mFeedbackListener;

    public void setFeedbackListener(OnFeedbackListener feedbackListener) {
        mFeedbackListener = feedbackListener;
    }

    public interface OnFeedbackListener {
        void onComment(Feedback feedback);

        void onDelete(Feedback feedback);
    }
}
